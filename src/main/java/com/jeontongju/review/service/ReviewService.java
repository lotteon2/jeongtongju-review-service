package com.jeontongju.review.service;

import com.jeontongju.review.client.ConsumerServiceClient;
import com.jeontongju.review.client.OrderServiceClient;
import com.jeontongju.review.client.ProductServiceClient;
import com.jeontongju.review.domain.Review;
import com.jeontongju.review.domain.ReviewSympathy;
import com.jeontongju.review.domain.ReviewSympathyId;
import com.jeontongju.review.dto.request.CreateReviewDto;
import com.jeontongju.review.dto.response.GetMyReviewDto;
import com.jeontongju.review.dto.response.GetReviewDto;
import com.jeontongju.review.dto.response.ReviewContentsDto;
import com.jeontongju.review.exception.ReviewNotFoundException;
import com.jeontongju.review.exception.common.CustomFailureException;
import com.jeontongju.review.kafka.ReviewProducer;
import com.jeontongju.review.mapper.ReviewMapper;
import com.jeontongju.review.repository.ReviewRepository;
import com.jeontongju.review.repository.ReviewSympathyRepository;
import com.jeontongju.review.repository.ReviewTagRepository;
import io.github.bitbox.bitbox.dto.ConsumerNameImageDto;
import io.github.bitbox.bitbox.enums.FailureTypeEnum;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    reviewRepository.save(
        reviewMapper.toReviewEntity(
            createReviewDto, memberId, productImageUrl, consumerNameImageDto));

    reviewProducer.updateReviewPoint(reviewMapper.toPointUpdateDto(memberId, createReviewDto));
  }

  @Transactional
  public void reviewSympathy(Long memberId, Long reviewId) {

    ReviewSympathyId reviewSympathyId =
        ReviewSympathyId.builder().consumerId(memberId).reviewId(reviewId).build();

    if (reviewSympathyRepository.existsById(reviewSympathyId)) {
      reviewSympathyRepository.deleteById(reviewSympathyId);
      Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
      review.setSympathy(review.getSympathy() - 1);
    } else {
      ReviewSympathy reviewSympathy =
          ReviewSympathy.builder()
              .id(reviewSympathyId)
              .reviewId(
                  reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new))
              .build();

      reviewSympathyRepository.save(reviewSympathy);
      Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
      review.setSympathy(review.getSympathy() + 1);
    }
  }

  public Page<GetMyReviewDto> getMyReview(Long memberId, Pageable pageable) {
    return reviewRepository.findMyReviewByConsumerId(memberId, pageable);
  }

  public GetReviewDto getProductReview(String productId, Long memberId, Pageable pageable) {
    List<ReviewContentsDto> reviewContentsDtoList = new ArrayList<>();

    Page<Review> reviewList = reviewRepository.findByProductId(productId, pageable);
    Boolean isSympathy = false;
    for (Review r : reviewList) {
      if (memberId != null) {
        isSympathy =
            reviewSympathyRepository.existsById(
                ReviewSympathyId.builder().reviewId(r.getReviewId()).consumerId(memberId).build());
      }

      reviewContentsDtoList.add(
          ReviewContentsDto.builder()
              .reviewId(r.getReviewId())
              .name(r.getName())
              .profileImageUrl(r.getProfileImageUrl())
              .reviewContents(r.getContents())
              .reviewPhotoImageUrl(r.getImageUrl())
              .reviewSympathyCount(reviewSympathyRepository.countByReviewId(r))
              .concept(reviewTagRepository.findNameByReviewId(r.getReviewId()))
              .createdAt(r.getCreatedAt())
              .isSympathy(isSympathy)
              .build());
    }

    return GetReviewDto.builder()
        .representativeReview(
            reviewTagRepository.findNameByProductId(productId, PageRequest.of(0, 2)))
        .histories(new PageImpl<>(reviewContentsDtoList, pageable, reviewList.getTotalElements()))
        .build();
  }
}
