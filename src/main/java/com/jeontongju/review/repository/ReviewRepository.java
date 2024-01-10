package com.jeontongju.review.repository;

import com.jeontongju.review.domain.Review;
import com.jeontongju.review.dto.response.GetMyReviewDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  @Query(
      "select new com.jeontongju.review.dto.response.GetMyReviewDto(r.reviewId, r.productId, r.productThumbnailImage, r.contents, r.imageUrl, r.createdAt) from Review r where r.isDeleted = false and r.consumerId = :memberId")
  Page<GetMyReviewDto> findMyReviewByConsumerId(Long memberId, Pageable pageable);
  Page<Review> findByProductIdAndIsDeleted(String productId, Boolean isDeleted, Pageable pageable);
  List<Review> findByProductIdAndIsDeleted(String productId, Boolean isDeleted);
  List<Review> findByName(String name);
  Page<Review> findBySellerId(Long memberId, Pageable pageable);
}
