package com.jeontongju.review.client;

import io.github.bitbox.bitbox.dto.ConsumerNameImageDto;
import io.github.bitbox.bitbox.dto.FeignFormat;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "consumer-service")
public interface ConsumerServiceClient {

  @CircuitBreaker(name = "ConsumerServiceClient@getConsumerNameImage")
  @GetMapping("/consumers/{consumerId}/name-image")
  FeignFormat<ConsumerNameImageDto> getConsumerNameImage(@PathVariable Long consumerId);
}
