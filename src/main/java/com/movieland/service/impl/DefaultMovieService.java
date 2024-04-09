package com.movieland.service.impl;

import com.movieland.entity.Movie;
import com.movieland.repository.MovieRepository;
import com.movieland.repository.MovieRepositoryCustom;
import com.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultMovieService implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieRepositoryCustom movieRepositoryCustom;


    @Override
    public List<Movie> findAllMovies(String sortBy, String sortOrder) {
        return movieRepositoryCustom.findAllCustomSortedMovies(sortBy, sortOrder);
    }

    @Override
    public List<Movie> findMoviesByGenre(int genreId, String sortBy, String sortOrder) {
        return movieRepositoryCustom.findAllByGenreIdCustomSortedMovies(genreId, sortBy, sortOrder);
    }

    @Override
    public Movie findMovieById(int movieId, String currency) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isPresent()) {
            if (currency != null) {
                return (convertCurrency(movie.get(), currency));
            }
            return movie.get();
        }
        return null;
    }

    @Override
    public List<Movie> findRandomMovies() {
        return movieRepository.findThreeRandomMovies();
    }

    private Movie convertCurrency(Movie movie, String currency) {
        String urlStart = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?valcode=";
        String urlMiddle = "&date=";
        String date = LocalDate.now().toString().replace("-", "");
        String urlEnd = "&json";

        StringJoiner stringJoiner = new StringJoiner("");
        stringJoiner.add(urlStart);
        stringJoiner.add(currency);
        stringJoiner.add(urlMiddle);
        stringJoiner.add(date);
        stringJoiner.add(urlEnd);
        String url = stringJoiner.toString();

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        double rate = Double.parseDouble(Objects.requireNonNull(StringUtils.substringBetween(response, "\"rate\":", ",\"cc\"")));
        double price = (movie.getPrice() / rate);
        final DecimalFormat decfor = new DecimalFormat("0.00");
        movie.setPrice(Double.parseDouble(decfor.format(price)));
        return movie;
    }

}



