package com.jeontongju.review.client;

import io.github.bitbox.bitbox.dto.FeignFormat;
import io.github.bitbox.bitbox.dto.ReviewDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service")
public interface OrderServiceClient {

  @CircuitBreaker(name = "OrderServiceClient@isOrderProductConfirmed")
  @GetMapping("/product-orders/{productOrderId}/review-verify")
  FeignFormat<ReviewDto> isOrderProductConfirmed(@PathVariable Long productOrderId);
}
