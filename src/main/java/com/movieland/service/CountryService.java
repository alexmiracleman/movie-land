package com.movieland.service;

import com.movieland.entity.Country;

import java.util.List;


public interface CountryService {

    List<Country> findAllById(List<Integer> countryIds);

    List<Country> findAllByMovieId(int movieId);
}
