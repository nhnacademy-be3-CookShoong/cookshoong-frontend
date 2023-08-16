package store.cookshoong.www.cookshoongfrontend.review.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 한 매장 대한 리뷰 조회 dto.
 *
 * @author seungyeon
 * @since 2023.08.13
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectReviewStoreResponseDto {

    private Long accountId;
    private String nickname;
    private SelectReviewResponseDto selectReviewResponse;
}
