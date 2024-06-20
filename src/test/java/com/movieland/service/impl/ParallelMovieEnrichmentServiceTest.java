package com.movieland.service.impl;

import com.movieland.dto.MovieDto;
import com.movieland.entity.Country;
import com.movieland.entity.Genre;
import com.movieland.entity.Review;
import com.movieland.service.CountryService;
import com.movieland.service.GenreService;
import com.movieland.service.ReviewService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static com.movieland.service.MovieEnrichmentService.EnrichmentType.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ParallelMovieEnrichmentServiceTest {

    @Autowired
    private ParallelMovieEnrichmentService parallelMovieEnrichmentService;

    @MockBean
    private GenreService genreService;
    @MockBean
    private CountryService countryService;
    @MockBean
    private ReviewService reviewService;


    MovieDto movieDto = MovieDto.builder()
            .id(1)
            .price(100.00)
            .description("Test movie")
            .nameNative("Terminator")
            .nameRussian("Терминатор")
            .picturePath("www.google.com")
            .rating(9.5)
            .yearOfRelease(1984)
            .build();

    Genre genre = new Genre();
    Country country = new Country();
    Review review = new Review();

    private List<Genre> genres;
    private List<Country> countries;
    private List<Review> reviews;

    @BeforeEach
    void setUp() {

        genre.setId(1);
        genre.setName("Comedy");
        genres = new ArrayList<>();
        genres.add(genre);

        country.setId(1);
        country.setName("USA");
        countries = new ArrayList<>();
        countries.add(country);

        review.setId(1);
        review.setText("Reviewing movie test");
        reviews = new ArrayList<>();
        reviews.add(review);

    }

    @Test
    public void enrichMovieWithGenresOnly() {

        when(genreService.findAllByMovieId(1)).thenReturn(genres);
        when(countryService.findAllByMovieId(1)).thenReturn(countries);
        when(reviewService.findAllByMovieId(1)).thenReturn(reviews);

        parallelMovieEnrichmentService.enrich(movieDto, GENRES);

        Assertions.assertEquals(1, movieDto.getGenres().size());
        Assertions.assertNull(movieDto.getCountries());
        Assertions.assertNull(movieDto.getReviews());

    }

    @Test
    public void enrichMovieWithGenresAndCountriesOnly() {


        when(genreService.findAllByMovieId(1)).thenReturn(genres);
        when(countryService.findAllByMovieId(1)).thenReturn(countries);
        when(reviewService.findAllByMovieId(1)).thenReturn(reviews);

        parallelMovieEnrichmentService.enrich(movieDto, GENRES, COUNTRIES);

        Assertions.assertEquals(1, movieDto.getGenres().size());
        Assertions.assertEquals(1, movieDto.getCountries().size());
        Assertions.assertNull(movieDto.getReviews());

    }

    @Test
    public void enrichMovieWithGenresCountriesAndReviews() {


        when(genreService.findAllByMovieId(1)).thenReturn(genres);
        when(countryService.findAllByMovieId(1)).thenReturn(countries);
        when(reviewService.findAllByMovieId(1)).thenReturn(reviews);

        parallelMovieEnrichmentService.enrich(movieDto, GENRES, COUNTRIES, REVIEWS);

        Assertions.assertEquals(1, movieDto.getGenres().size());
        Assertions.assertEquals(1, movieDto.getCountries().size());
        Assertions.assertEquals(1, movieDto.getReviews().size());

    }

    @Test
    public void enrichMovieWithGenresCountriesAndReviewsGetGenresTimeoutException() throws InterruptedException {

        when(genreService.findAllByMovieId(1)).thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                long start = System.currentTimeMillis();
                int sum = 0;
                for (int i = 0; i < 20_000; i++) {
                    for (int i1 = 0; i1 < 10_000_00; i1++) {
                        sum += i + i1;
                    }
                }

                System.out.println(sum);

                System.out.println(System.currentTimeMillis() - start);
                return genres;
            }
        });

        when(countryService.findAllByMovieId(1)).thenReturn(countries);
        when(reviewService.findAllByMovieId(1)).thenReturn(reviews);

        parallelMovieEnrichmentService.enrich(movieDto, GENRES, COUNTRIES, REVIEWS);
        Thread.sleep(10000);
        Assertions.assertNull(movieDto.getGenres());
        Assertions.assertEquals(1, movieDto.getCountries().size());
        Assertions.assertEquals(1, movieDto.getReviews().size());

    }
    @Test
    public void enrichMovieWithGenresCountriesAndReviewsGetGenresAndCountriesTimeoutException() throws InterruptedException {

        when(genreService.findAllByMovieId(1)).thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                long start = System.currentTimeMillis();
                int sum = 0;
                for (int i = 0; i < 20_000; i++) {
                    for (int i1 = 0; i1 < 10_000_00; i1++) {
                        sum += i + i1;
                    }
                }

                System.out.println(sum);

                System.out.println(System.currentTimeMillis() - start);
                return genres;
            }
        });

        when(countryService.findAllByMovieId(1)).thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                long start = System.currentTimeMillis();
                int sum = 0;
                for (int i = 0; i < 20_000; i++) {
                    for (int i1 = 0; i1 < 10_000_00; i1++) {
                        sum += i + i1;
                    }
                }

                System.out.println(sum);

                System.out.println(System.currentTimeMillis() - start);
                return countries;
            }
        });
        when(reviewService.findAllByMovieId(1)).thenReturn(reviews);

        parallelMovieEnrichmentService.enrich(movieDto, GENRES, COUNTRIES, REVIEWS);
        Thread.sleep(10000);
        Assertions.assertNull(movieDto.getGenres());
        Assertions.assertNull(movieDto.getCountries());
        Assertions.assertEquals(1, movieDto.getReviews().size());

    }


//    public static void main(String[] args) {
//        long start = System.currentTimeMillis();
//        int sum = 0;
//        for (int i = 0; i < 20_000; i++) {
//            for (int i1 = 0; i1 < 10_000_00; i1++) {
//                sum += i + i1;
//            }
//        }
//        System.out.println(sum);
//        System.out.println(System.currentTimeMillis() - start);
//    }

}