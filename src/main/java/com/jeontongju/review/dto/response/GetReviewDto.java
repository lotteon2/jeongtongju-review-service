package com.jeontongju.review.dto.response;

import com.jeontongju.review.enums.ConceptTypeEnum;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class GetReviewDto {
  private List<ConceptTypeEnum> representativeReview;
  private Page<ReviewContentsDto> content;
}
