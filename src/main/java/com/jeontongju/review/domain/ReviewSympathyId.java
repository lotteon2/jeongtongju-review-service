package com.jeontongju.review.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Embeddable
public class ReviewSympathyId implements Serializable {

    private Long reviewId;

    @Column(name = "consumer_id", nullable = false)
    private Long consumerId;
}
