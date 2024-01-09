package com.jeontongju.review.controller;

import com.jeontongju.review.dto.request.CreateReviewDto;
import com.jeontongju.review.dto.response.GetMyReviewDto;
import com.jeontongju.review.dto.response.GetReviewDto;
import com.jeontongju.review.service.ReviewService;
import io.github.bitbox.bitbox.dto.ResponseFormat;
import io.github.bitbox.bitbox.enums.MemberRoleEnum;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class ReviewRestController {

  private final ReviewService reviewService;

  @PostMapping("/products/reviews")
  public ResponseEntity<ResponseFormat<Void>> createReview(
      @RequestHeader Long memberId,
      @RequestHeader MemberRoleEnum memberRole,
      @Valid @RequestBody CreateReviewDto createReviewDto) {

    reviewService.createReview(memberId, createReviewDto);

    return ResponseEntity.ok()
        .body(
            ResponseFormat.<Void>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .detail("리뷰 작성 성공")
                .build());
  }

  @PostMapping("/reviews/{reviewId}/sympathy")
  public ResponseEntity<ResponseFormat<Void>> reviewSympathy(
      @RequestHeader Long memberId,
      @RequestHeader MemberRoleEnum memberRole,
      @PathVariable Long reviewId) {

    reviewService.reviewSympathy(memberId, reviewId);

    return ResponseEntity.ok()
        .body(
            ResponseFormat.<Void>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .detail("리뷰 공감 상태 변경 성공")
                .build());
  }

  @GetMapping("/reviews")
  public ResponseEntity<ResponseFormat<Page<GetMyReviewDto>>> getMyReview(
      @RequestHeader Long memberId,
      @RequestHeader MemberRoleEnum memberRole,
      @PageableDefault(page = 0, sort = "createdAt", direction = Sort.Direction.DESC, size = 10)
          Pageable pageable) {

    return ResponseEntity.ok()
        .body(
            ResponseFormat.<Page<GetMyReviewDto>>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .detail("내 리뷰 조회 성공")
                .data(reviewService.getMyReview(memberId, pageable))
                .build());
  }

  @GetMapping("/products/{productId}/reviews")
  public ResponseEntity<ResponseFormat<GetReviewDto>> getProductReview(
      @RequestHeader(required = false) Long memberId,
      @RequestHeader(required = false) MemberRoleEnum memberRole,
      @PathVariable String productId,
      @PageableDefault(page = 0, sort = "sympathy", direction = Sort.Direction.DESC, size = 10)
          Pageable pageable) {

    return ResponseEntity.ok()
        .body(
            ResponseFormat.<GetReviewDto>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .detail("상품 리뷰 조회 성공")
                .data(reviewService.getProductReview(productId, memberId, pageable))
                .build());
  }
}
