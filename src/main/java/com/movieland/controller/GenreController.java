package com.movieland.controller;

import com.movieland.dto.GenreDto;
import com.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class GenreController {

    private final GenreService genreService;


    @GetMapping("/genre")
    public List<GenreDto> findAllGenres() {
        return genreService.findAll();
    }

}









