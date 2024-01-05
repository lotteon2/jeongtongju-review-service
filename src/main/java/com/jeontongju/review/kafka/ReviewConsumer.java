package com.jeontongju.review.kafka;

import com.jeontongju.review.service.ReviewService;
import io.github.bitbox.bitbox.dto.ConsumerNameImageDto;
import io.github.bitbox.bitbox.dto.ProductImageInfoDto;
import io.github.bitbox.bitbox.util.KafkaTopicNameInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReviewConsumer {

    private final ReviewService reviewService;

    @KafkaListener(topics = KafkaTopicNameInfo.UPDATE_PRODUCT_TO_REVIEW)
    public void updateReviewByProduct(ProductImageInfoDto productImageInfoDto) {
        reviewService.updateReviewByProduct(productImageInfoDto);
    }

    @KafkaListener(topics = KafkaTopicNameInfo.DELETE_PRODUCT_TO_REVIEW)
    public void deleteReviewByProduct(List<String> productIds) {
        reviewService.deleteReview(productIds);
    }

    @KafkaListener(topics = KafkaTopicNameInfo.UPDATE_CONSUMER_TO_REVIEW)
    public void deleteReviewByProduct(ConsumerNameImageDto consumerNameImageDto) {
        reviewService.updateReviewByConsumer(consumerNameImageDto);
    }
}
