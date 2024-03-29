package com.movieland.repository;

import com.movieland.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query(value = "SELECT id, name_native, name_russian, poster_id, price, rating, \n" +
                    "year_of_release FROM movies ORDER BY random() LIMIT 3", nativeQuery = true)
    List<Movie> findThreeRandomMovies();

    List<Movie> findAllByOrderByRatingDesc();

    List<Movie> findAllByOrderByPriceAsc();

    List<Movie> findAllByOrderByPriceDesc();

}
