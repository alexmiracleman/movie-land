package com.movieland.service;

import com.movieland.dto.MovieDto;

public interface MovieEnrichmentService {

    void enrich(MovieDto movieDto, EnrichmentType... types);

    enum EnrichmentType {
        COUNTRIES,
        GENRES,
        REVIEWS

    }

}
