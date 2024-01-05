package com.jeontongju.review.repository;

import com.jeontongju.review.domain.Review;
import com.jeontongju.review.dto.response.GetMyReviewDto;
import com.jeontongju.review.dto.response.GetReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  @Query(
      "select new com.jeontongju.review.dto.response.GetMyReviewDto(r.reviewId, r.productId, r.productThumbnailImage, r.contents, r.imageUrl, r.createdAt) from Review r where r.isDeleted = false and r.consumerId = :memberId")
  Page<GetMyReviewDto> findMyReviewByConsumerId(Long memberId, Pageable pageable);

  Page<Review> findByProductId(String productId, Pageable pageable);
  List<Review> findByProductId(String productId);
  List<Review> findByName(String name);
}
