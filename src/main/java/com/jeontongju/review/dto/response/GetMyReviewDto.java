package com.jeontongju.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class GetMyReviewDto {
  private Long reviewId;
  private String productId;
  private String productThumbnailImage;
  private String reviewContents;
  private String reviewPhotoImageUrl;
  private LocalDateTime createdAt;
}
