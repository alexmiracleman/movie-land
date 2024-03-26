package com.movieland.service.impl;

import com.movieland.entity.Genre;
import com.movieland.entity.Movie;
import com.movieland.repository.GenreRepository;
import com.movieland.repository.MovieRepository;
import com.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultMovieService implements MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;


    @Override
    public List<Movie> getAllMovies(String rating, String price) {
        if (rating == null && price != null) {
            if (price.equals("asc")) {
                return movieRepository.findAllByOrderByPriceAsc();
            }
            if (price.equals("desc")) {
                return movieRepository.findAllByOrderByPriceDesc();
            }
        }
        if (price == null && rating != null) {
            if (rating.equals("desc")) {
                return movieRepository.findAllByOrderByRatingDesc();
            }
        }
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> getRandomMovies() {
        List<Movie> allMovies = movieRepository.findAll();
        List<Integer> random = new Random().ints(0, allMovies.size())
                .distinct()
                .limit(3)
                .boxed()
                .toList();
        return (List.of(allMovies.get(random.get(0)), allMovies.get(random.get(1)), allMovies.get(random.get(2))));
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId, String rating, String price) {
        Optional<Genre> genre = genreRepository.findById(genreId);
        if (genre.isPresent()) {
            if (rating == null && price != null) {
                if (price.equals("asc")) {
                    return movieRepository.findAllByGenreContainingOrderByPriceAsc(genre.get().getName());
                }
                if (price.equals("desc")) {
                    return movieRepository.findAllByGenreContainingOrderByPriceDesc(genre.get().getName());
                }
            }
            if (price == null && rating != null) {
                if (rating.equals("desc")) {
                    return movieRepository.findAllByGenreContainingOrderByRatingDesc(genre.get().getName());
                }
            }
            if (price == null && rating == null) {
                return movieRepository.findAllByGenreContaining(genre.get().getName());
            }
        }
        return movieRepository.findAll();
    }

}
