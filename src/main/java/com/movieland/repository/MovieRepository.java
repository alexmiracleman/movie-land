package com.movieland.repository;

import com.movieland.entity.Movie;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer>, MovieRepositoryCustom {

    @Query(value = "SELECT * FROM movies ORDER BY random() LIMIT 3", nativeQuery = true)
    List<Movie> findThreeRandomMovies();

    @EntityGraph(attributePaths = {"countries", "genres", "reviews"})
    Optional<Movie> findById(int movieId);
//
//    @Override
//    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
//    <S extends Movie> S save(S entity);


}
