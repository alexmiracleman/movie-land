package com.movieland.service;

import com.movieland.dto.ReviewToSaveDto;

public interface ReviewService {

    void saveReview(ReviewToSaveDto review, String authHeader);
}
