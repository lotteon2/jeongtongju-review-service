package com.jeontongju.review.service;

import com.jeontongju.review.repository.ReviewRepository;
import com.jeontongju.review.repository.ReviewSympathyRepository;
import com.jeontongju.review.repository.ReviewTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final ReviewSympathyRepository reviewSympathyRepository;
  private final ReviewTagRepository reviewTagRepository;
}
