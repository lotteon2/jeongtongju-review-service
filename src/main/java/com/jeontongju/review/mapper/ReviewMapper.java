package com.jeontongju.review.mapper;

import com.jeontongju.review.domain.Review;
import com.jeontongju.review.domain.ReviewTag;
import com.jeontongju.review.dto.request.CreateReviewDto;
import com.jeontongju.review.enums.ConceptTypeEnum;
import io.github.bitbox.bitbox.dto.ConsumerNameImageDto;
import io.github.bitbox.bitbox.dto.PointUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewMapper {

  public Review toReviewEntity(
      CreateReviewDto createReviewDto,
      Long memberId,
      String productImageUrl,
      ConsumerNameImageDto consumerNameImageDto) {

    Review review =
        Review.builder()
            .productId(createReviewDto.getProductId())
            .consumerId(memberId)
            .contents(createReviewDto.getReviewContents())
            .imageUrl(createReviewDto.getReviewPhotoImageUrl())
            .name(consumerNameImageDto.getName())
            .profileImageUrl(consumerNameImageDto.getImageUrl())
            .productThumbnailImage(productImageUrl)
            .build();

    if (createReviewDto.getConcept().size() != 0) {
      for (ConceptTypeEnum concept : createReviewDto.getConcept()) {
        ReviewTag reviewTag =
            ReviewTag.builder()
                .productId(createReviewDto.getProductId())
                .name(concept)
                .review(review)
                .build();
        review.getReviewTag().add(reviewTag);
      }
    }
    return review;
  }

  public PointUpdateDto toPointUpdateDto(Long consumerId, CreateReviewDto createReviewDto) {

    return PointUpdateDto.builder()
        .consumerId(consumerId)
        .point(createReviewDto.getReviewPhotoImageUrl() == null ? 300L : 500L)
        .build();
  }
}
