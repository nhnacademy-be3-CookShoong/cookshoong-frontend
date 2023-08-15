package store.cookshoong.www.cookshoongfrontend.review.model.response;

import java.time.LocalDateTime;
import java.util.List;
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
    @Setter
    private String storeImageName;
    private String storeImageLocationType;
    private String storeImageDomainName;
    private Long reviewId;
    private String contents;
    private Integer rating;
    private LocalDateTime writtenAt;
    private LocalDateTime updatedAt;
    @Setter
    private List<SelectReviewImageResponseDto> imageResponseDtos;
    private List<SelectReviewOrderMenuResponseDto> menuResponseDtos;
    private List<SelectBusinessReviewResponseDto> replyResponseDtos;
}
