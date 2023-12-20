package com.jeontongju.review.service;

import com.jeontongju.review.domain.Review;
import com.jeontongju.review.domain.ReviewSympathyId;
import com.jeontongju.review.repository.ReviewRepository;
import com.jeontongju.review.repository.ReviewSympathyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ReviewServiceTest {

  @Autowired private ReviewService reviewService;
  @Autowired private ReviewRepository reviewRepository;
  @Autowired private ReviewSympathyRepository reviewSympathyRepository;

  private Review initReview() {
    Review review =
        Review.builder()
            .productId("280a8a4d-a27f-4d01-b031-2a003cc4c039")
            .consumerId(1L)
            .contents("짱이다")
            .name("최소영")
            .imageUrl("/example")
            .profileImageUrl("/example")
            .productThumbnailImage("/example")
            .build();

    return reviewRepository.save(review);
  }

  private void initReviewSympathy(Long memberId, Long reviewId) {
    reviewService.reviewSympathy(memberId, reviewId);
  }

  @Test
  @DisplayName("TEST - create reviewSympathy ")
  void createReview() {

    Review review = initReview();
    Long memberId = 1L;

    reviewService.reviewSympathy(memberId, review.getReviewId());

    ReviewSympathyId reviewSympathyId =
        ReviewSympathyId.builder().consumerId(memberId).reviewId(review.getReviewId()).build();

    Assertions.assertThat(reviewSympathyRepository.existsById(reviewSympathyId)).isSameAs(true);
  }

  @Test
  @DisplayName("TEST - delete reviewSympathy ")
  void deleteReview() {
    Review review = initReview();
    initReviewSympathy(1L, review.getReviewId());

    reviewService.reviewSympathy(1L, review.getReviewId());

    ReviewSympathyId reviewSympathyId =
        ReviewSympathyId.builder().consumerId(1L).reviewId(review.getReviewId()).build();

    Assertions.assertThat(reviewSympathyRepository.existsById(reviewSympathyId)).isSameAs(false);
  }
}
