package store.cookshoong.www.cookshoongfrontend.review.model.response;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사업자 리뷰 조회 dto.
 *
 * @author seungyeon
 * @since 2023.08.13
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectBusinessReviewResponseDto {
    private Long reviewReplyId;
    private String contents;
    private LocalDateTime writtenAt;
}
