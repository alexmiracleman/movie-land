package com.movieland.repository.impl;

import com.movieland.entity.Genre;
import com.movieland.entity.Movie;
import com.movieland.repository.MovieRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DefaultMovieRepositoryCustom implements MovieRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Movie> findAllCustomSortedMovies(String sortBy, String sortOrder) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);

        Root<Movie> movieRoot = cq.from(Movie.class);

        if (sortOrder.equals("asc")) {
            cq.orderBy(
                    cb.asc(movieRoot.get(sortBy)));
        } else {
            cq.orderBy(
                    cb.desc(movieRoot.get(sortBy)));
        }

        cq.select(movieRoot);

        TypedQuery<Movie> query = em.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List<Movie> findAllByGenreIdCustomSortedMovies(int genreId, String sortBy, String sortOrder) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> movie = cq.from(Movie.class);
        List<Predicate> predicates = new ArrayList<>();

        Subquery<Long> subquery = cq.subquery(Long.class);
        Root<Movie> subqueryMovie = subquery.from(Movie.class);
        Join<Genre, Movie> subqueryGenre = subqueryMovie.join("genres");

        subquery.select(subqueryMovie.get("id")).where(
                cb.equal(subqueryGenre.get("id"), genreId));

        predicates.add(cb.in(movie.get("id")).value(subquery));
        cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        if (sortOrder.equals("asc")) {
            cq.orderBy(
                    cb.asc(movie.get(sortBy)));
        } else {
            cq.orderBy(
                    cb.desc(movie.get(sortBy)));
        }
        TypedQuery<Movie> query = em.createQuery(cq);
        return query.getResultList();
    }

}
