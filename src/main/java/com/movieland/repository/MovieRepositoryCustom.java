package com.movieland.repository;

import com.movieland.entity.Movie;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface MovieRepositoryCustom {
    List<Movie> findAllCustomSortedMovies(String sortBy, String sortOrder);

    List<Movie> findAllByGenreIdCustomSortedMovies(int genreId, String sortBy, String sortOrder);
}
