package com.jeontongju.review.controller;

import com.jeontongju.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class ReviewRestController {

    private final ReviewService reviewService;

}
