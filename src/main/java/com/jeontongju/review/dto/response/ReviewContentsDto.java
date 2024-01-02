package com.jeontongju.review.dto.response;

import com.jeontongju.review.enums.ConceptTypeEnum;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ReviewContentsDto {
  private Long reviewId;
  private String name;
  private String profileImageUrl;
  private List<ConceptTypeEnum> concept;
  private Long reviewSympathyCount;
  private LocalDateTime createdAt;
  private Boolean isSympathy;

  public ReviewContentsDto(
      Long reviewId,
      String name,
      String profileImageUrl,
      List<ConceptTypeEnum> concept,
      Long reviewSympathyCount,
      LocalDateTime createdAt) {
    this.reviewId = reviewId;
    this.name = name;
    this.profileImageUrl = profileImageUrl;
    this.concept = concept;
    this.reviewSympathyCount = reviewSympathyCount;
    this.createdAt = createdAt;
    this.isSympathy = false;
  }
}
