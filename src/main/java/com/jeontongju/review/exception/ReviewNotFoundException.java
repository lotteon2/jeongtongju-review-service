package com.jeontongju.review.exception;


import com.jeontongju.review.exception.common.EntityNotFoundException;

public class ReviewNotFoundException extends EntityNotFoundException {

    private static final String message = "리뷰을 찾을 수 없습니다";

    public ReviewNotFoundException() {
        super(message);
    }
    
}