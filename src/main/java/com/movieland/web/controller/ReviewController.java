package com.movieland.web.controller;

import com.movieland.dto.ReviewToSaveDto;
import com.movieland.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/review")
public class ReviewController {

    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);

    private final ReviewService reviewService;


    @PostMapping()
    public void addReview(
            @RequestBody ReviewToSaveDto review,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader
    ) {
        log.info("Posting review");
        reviewService.saveReview(review, authHeader);
    }

}









