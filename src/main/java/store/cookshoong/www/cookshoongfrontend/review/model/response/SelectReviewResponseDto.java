package store.cookshoong.www.cookshoongfrontend.review.model.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 한 주문에 대한 리뷰 조회 dto.
 *
 * @author seungyeon
 * @since 2023.08.13
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectReviewResponseDto {
    private Long storeId;
    private String storeName;

    private String storeImageName;
    private String storeImageLocationType;
    private String storeImageDomainName;
    private Long reviewId;
    private String contents;
    private Integer rating;
    private LocalDateTime writtenAt;
    private LocalDateTime updatedAt;

    private Set<SelectReviewImageResponseDto> imageResponses;
    private Set<SelectReviewOrderMenuResponseDto> menuResponses;
    private Set<SelectBusinessReviewResponseDto> replyResponses;
}
