package com.movieland.repository;

import com.movieland.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    List<Movie> findAllByGenreContaining(String genre);

    List<Movie> findAllByOrderByRatingDesc();


    List<Movie> findAllByOrderByPriceAsc();

    List<Movie> findAllByOrderByPriceDesc();

    List<Movie> findAllByGenreContainingOrderByPriceAsc(String genre);

    List<Movie> findAllByGenreContainingOrderByPriceDesc(String genre);

    List<Movie> findAllByGenreContainingOrderByRatingDesc(String genre);
}
