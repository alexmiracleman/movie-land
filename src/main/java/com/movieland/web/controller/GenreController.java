package com.movieland.web.controller;

import com.movieland.dto.GenreDto;
import com.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/genre")
public class GenreController {

    private static final Logger log = LoggerFactory.getLogger(GenreController.class);

    private final GenreService genreService;


    @GetMapping
    public List<GenreDto> findAllGenres() {
        log.info("Getting all genres");
        return genreService.findAll();
    }

}









