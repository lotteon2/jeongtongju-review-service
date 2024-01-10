package com.jeontongju.review.client;

import io.github.bitbox.bitbox.dto.FeignFormat;
import io.github.bitbox.bitbox.dto.SellerProductInfoDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductServiceClient {

  @CircuitBreaker(name = "ProductServiceClient@getProductImage")
  @GetMapping("/products/{productId}/image")
  FeignFormat<SellerProductInfoDto> getProductSeller(@PathVariable String productId);
}
