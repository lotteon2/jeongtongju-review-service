package com.jeontongju.review.repository;

import com.jeontongju.review.domain.Review;
import com.jeontongju.review.domain.ReviewSympathy;
import com.jeontongju.review.domain.ReviewSympathyId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewSympathyRepository extends JpaRepository<ReviewSympathy, ReviewSympathyId> {
    Long countByReviewId(Review review);
}
