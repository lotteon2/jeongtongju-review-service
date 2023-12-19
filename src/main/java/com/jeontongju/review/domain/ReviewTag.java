package com.jeontongju.review.domain;

import com.jeontongju.review.enums.ConceptTypeEnum;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "review_tag")
public class ReviewTag {

    @Id
    @Column(name = "review_tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewTagId;

    @JoinColumn(name = "review_id")
    @ManyToOne(targetEntity = Review.class, fetch = FetchType.LAZY)
    private Review reviewId;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private ConceptTypeEnum name;

}
