package com.movieland.service;

import com.movieland.entity.Movie;

public interface MovieEnrichmentService {

    void enrich(Movie movie, EnrichmentType... types);

    enum EnrichmentType {
        COUNTRIES,
        GENRES,
        REVIEWS
    }

}
