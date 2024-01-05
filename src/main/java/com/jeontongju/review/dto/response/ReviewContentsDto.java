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
  private String reviewContents;
  private String reviewPhotoImageUrl;
  private List<ConceptTypeEnum> concept;
  private Long reviewSympathyCount;
  private LocalDateTime createdAt;
  private Boolean isSympathy;
}
