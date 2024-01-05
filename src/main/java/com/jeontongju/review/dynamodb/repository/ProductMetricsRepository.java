package com.jeontongju.review.dynamodb.repository;

import com.jeontongju.review.dynamodb.domian.ProductMetrics;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface ProductMetricsRepository extends CrudRepository<ProductMetrics, String> {}
