package store.cookshoong.www.cookshoongfrontend.review.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 사용자가 등록한 리뷰 이미지들 조회.
 *
 * @author seungyeon
 * @since 2023.08.13
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectReviewImageResponseDto {
    @Setter
    private String savedName;
    private String locationType;
    private String domainName;
}
