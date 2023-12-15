package com.jeontongju.review.domain;

import com.jeontongju.review.domain.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "review")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "consumer_id", nullable = false)
    private Long consumerId;

    @Column(name = "contents", nullable = false, columnDefinition = "TEXT")
    private String contents;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "product_thumbnail_image", nullable = false)
    private String productThumbnailImage;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

}
