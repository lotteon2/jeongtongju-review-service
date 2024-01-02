package com.jeontongju.review.repository;

import com.jeontongju.review.domain.ReviewTag;
import com.jeontongju.review.enums.ConceptTypeEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewTagRepository extends JpaRepository<ReviewTag, Long> {

    @Query("select t.name from ReviewTag t where t.review.reviewId = :reviewId ")
    List<ConceptTypeEnum> findNameByReviewId(Long reviewId);

    @Query("select t.name from ReviewTag t where t.review.productId = :productId group by t.name order by count(t.name) desc")
    List<ConceptTypeEnum> findNameByProductId(String productId, Pageable pageable);
}
