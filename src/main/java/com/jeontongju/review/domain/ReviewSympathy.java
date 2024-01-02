package com.jeontongju.review.domain;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "review_sympathy")
public class ReviewSympathy implements Serializable {

    @EmbeddedId
    private ReviewSympathyId id;

    @MapsId("reviewId")  //외래키와 매핑한 연관관계를 기본 키에도 매핑하겠다. @EmbeddedId를 사용한 식별자 클래스의 기본 키 필드를 지정
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review reviewId;

}
