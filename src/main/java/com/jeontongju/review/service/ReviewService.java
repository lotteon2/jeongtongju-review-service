package com.jeontongju.review.service;

import com.jeontongju.review.client.ConsumerServiceClient;
import com.jeontongju.review.client.OrderServiceClient;
import com.jeontongju.review.client.ProductServiceClient;
import com.jeontongju.review.domain.Review;
import com.jeontongju.review.domain.ReviewTag;
import com.jeontongju.review.dto.request.CreateReviewDto;
import com.jeontongju.review.enums.ConceptTypeEnum;
import com.jeontongju.review.exception.common.CustomFailureException;
import com.jeontongju.review.kafka.ReviewProducer;
import com.jeontongju.review.mapper.ReviewMapper;
import com.jeontongju.review.repository.ReviewRepository;
import com.jeontongju.review.repository.ReviewSympathyRepository;
import com.jeontongju.review.repository.ReviewTagRepository;
import io.github.bitbox.bitbox.dto.ConsumerNameImageDto;
import io.github.bitbox.bitbox.enums.FailureTypeEnum;
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
  private final ProductServiceClient productServiceClient;
  private final ConsumerServiceClient consumerServiceClient;
  private final OrderServiceClient orderServiceClient;
  private final ReviewMapper reviewMapper;
  private final ReviewProducer reviewProducer;

  @Transactional
  public void createReview(Long memberId, CreateReviewDto createReviewDto) {

    if (!orderServiceClient
        .isOrderProductConfirmed(createReviewDto.getProductOrderId())
        .getData()) {
      throw new CustomFailureException(FailureTypeEnum.NOT_ORDER_CONFIRM);
    }

    String productImageUrl =
        productServiceClient.getProductImage(createReviewDto.getProductId()).getData();

    ConsumerNameImageDto consumerNameImageDto =
        consumerServiceClient.getConsumerNameImage(memberId).getData();

    Review review =
        reviewMapper.toReviewEntity(
            createReviewDto, memberId, productImageUrl, consumerNameImageDto);

    Review savedReview = reviewRepository.save(review);

    if (createReviewDto.getConcept().size() != 0) {
      for (ConceptTypeEnum concept : createReviewDto.getConcept()) {
        reviewTagRepository.save(
            ReviewTag.builder()
                .reviewId(savedReview)
                .productId(createReviewDto.getProductId())
                .name(concept)
                .build());
      }
    }

    reviewProducer.updateReviewPoint(reviewMapper.toPointUpdateDto(memberId, createReviewDto));
  }
}
