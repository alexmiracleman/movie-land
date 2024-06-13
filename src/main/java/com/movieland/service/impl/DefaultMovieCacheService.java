//package com.movieland.service.impl;
//
//import com.movieland.dto.MovieDto;
//import com.movieland.service.MovieCacheService;
//import com.movieland.service.MovieService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Service;
//
//import java.lang.ref.SoftReference;
//import java.util.concurrent.ConcurrentHashMap;
//
//
//@Service
//@Slf4j
//@Primary
//public class DefaultMovieCacheService implements MovieCacheService {
//
//    MovieService movieService;
//
//    @Autowired
//    public DefaultMovieCacheService(@Lazy MovieService movieService) {
//        this.movieService = movieService;
//    }
//
//    private final ConcurrentHashMap<Integer, SoftReference<MovieDto>> cache = new ConcurrentHashMap<>();
//
//    @Override
//    public MovieDto get(int movieId) {
//
//        SoftReference<MovieDto> dtoSoftReference = cache.compute(movieId, (key, val) -> {
//            if (val == null || val.get() == null) {
//                return new SoftReference<>(movieService.findInDbAndEnrich(movieId));
//            }
//            return val;
//        });
//        return dtoSoftReference.get();
//    }
//
//    @Override
//    public void put(int movieId, MovieDto movieDto) {
//        cache.put(movieId, new SoftReference<>(movieDto));
//    }
//
//}
