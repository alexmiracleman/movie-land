package com.movieland.repository;

import com.movieland.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query(value = "SELECT * FROM movies ORDER BY random() LIMIT 3", nativeQuery = true)
    List<Movie> findThreeRandomMovies();

}
