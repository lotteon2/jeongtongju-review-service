package com.jeontongju.review.repository;

import com.jeontongju.review.domain.Review;
import com.jeontongju.review.domain.ReviewSympathy;
import com.jeontongju.review.domain.ReviewSympathyId;
import com.jeontongju.review.domain.ReviewTag;
import com.jeontongju.review.enums.ConceptTypeEnum;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ReviewRepositoryTest {

  @Autowired private ReviewRepository reviewRepository;
  @Autowired private ReviewSympathyRepository reviewSympathyRepository;
  @Autowired private ReviewTagRepository reviewTagRepository;

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

  private void initReviewTag() {

    List<ConceptTypeEnum> conceptTypeEnumList =
        List.of(ConceptTypeEnum.TRIP, ConceptTypeEnum.CAMPING);

    Review review = initReview();

    for (ConceptTypeEnum concept : conceptTypeEnumList) {
      ReviewTag reviewTag =
          ReviewTag.builder()
              .review(review)
              .productId(review.getProductId())
              .name(concept)
              .build();
      ReviewTag savedReviewTag = reviewTagRepository.save(reviewTag);
    }
  }

  private ReviewSympathy initReviewSympathy() {
    Review review = initReview();

    ReviewSympathyId reviewSympathyId =
            ReviewSympathyId.builder().reviewId(review.getReviewId()).consumerId(1L).build();

    ReviewSympathy reviewSympathy =
            ReviewSympathy.builder().id(reviewSympathyId).reviewId(review).build();

    return reviewSympathyRepository.save(reviewSympathy);
  }

  @Test
  @DisplayName("TEST - create review")
  void createReview() {

    Review review =
        Review.builder()
            .productId("280a8a4d-a27f-4d01-b031-2a003cc4c039")
            .consumerId(1L)
            .contents("짱이다")
            .name("최소영")
            .profileImageUrl("/example")
            .productThumbnailImage("/example")
            .build();

    Review savedReview = reviewRepository.save(review);
    Assertions.assertThat(savedReview.getReviewId()).isNotNull();
    Assertions.assertThat(savedReview.getContents()).isSameAs(review.getContents());
    Assertions.assertThat(savedReview.getImageUrl()).isSameAs(null);
    Assertions.assertThat(savedReview.getIsDeleted()).isSameAs(false);
  }

  @Test
  @DisplayName("TEST - create review with Image")
  void createReviewWithImage() {

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

    Review savedReview = reviewRepository.save(review);
    Assertions.assertThat(savedReview.getContents()).isSameAs(review.getContents());
    Assertions.assertThat(savedReview.getImageUrl()).isSameAs(review.getImageUrl());
  }

  @Test
  @DisplayName("TEST - create review with Tag")
  void createReviewWithTag() {

    List<ConceptTypeEnum> conceptTypeEnumList =
        List.of(ConceptTypeEnum.TRIP, ConceptTypeEnum.CAMPING);

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

    Review savedReview = reviewRepository.save(review);
    Assertions.assertThat(savedReview.getContents()).isSameAs(review.getContents());

    for (ConceptTypeEnum concept : conceptTypeEnumList) {

      ReviewTag reviewTag =
          ReviewTag.builder()
              .review(review)
              .productId(review.getProductId())
              .name(concept)
              .build();

      ReviewTag savedReviewTag = reviewTagRepository.save(reviewTag);

      Assertions.assertThat(savedReviewTag.getReviewTagId()).isNotNull();
      Assertions.assertThat(savedReviewTag.getName()).isSameAs(concept);
    }
  }

  @Test
  @DisplayName("TEST - get review")
  void getReview() {
    Review review = initReview();
    Review getReview = reviewRepository.findById(review.getReviewId()).get();
    Assertions.assertThat(getReview.getContents()).isSameAs(review.getContents());
  }

  @Test
  @DisplayName("TEST - create review sympathy")
  void createReviewSympathy() {

    Review review = initReview();

    ReviewSympathyId reviewSympathyId =
        ReviewSympathyId.builder().reviewId(review.getReviewId()).consumerId(1L).build();

    ReviewSympathy reviewSympathy =
        ReviewSympathy.builder().id(reviewSympathyId).reviewId(review).build();

    ReviewSympathy savedReviewSympathy = reviewSympathyRepository.save(reviewSympathy);

    Assertions.assertThat(savedReviewSympathy.getReviewId()).isSameAs(reviewSympathy.getReviewId());
    Assertions.assertThat(savedReviewSympathy.getId()).isSameAs(reviewSympathy.getId());
  }

  @Test
  @DisplayName("TEST - delete review sympathy")
  void deleteReviewSympathy() {

    ReviewSympathy savedReviewSympathy = initReviewSympathy();

    ReviewSympathyId reviewSympathyId = ReviewSympathyId.builder().reviewId(savedReviewSympathy.getReviewId().getReviewId())
            .consumerId(savedReviewSympathy.getReviewId().getConsumerId()).build();

    reviewSympathyRepository.deleteById(reviewSympathyId);

    Assertions.assertThat(reviewSympathyRepository.findById(reviewSympathyId)).isNotNull();
  }
}
