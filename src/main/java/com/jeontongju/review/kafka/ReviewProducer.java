package com.jeontongju.review.kafka;

import io.github.bitbox.bitbox.util.KafkaTopicNameInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewProducer<T> {

  private final KafkaTemplate<String, T> kafkaTemplate;

  public void updateReviewPoint(T pointUpdateDto) {
    kafkaTemplate.send(KafkaTopicNameInfo.UPDATE_REVIEW_POINT, pointUpdateDto);
  }

  public void updateProductOrderReviewStatus(T productOrderId) {
    kafkaTemplate.send(KafkaTopicNameInfo.PRODUCT_ORDER_REVIEW_WRITE_STATUS_UPDATE, productOrderId);
  }
}
