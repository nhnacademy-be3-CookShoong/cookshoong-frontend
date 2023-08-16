package store.cookshoong.www.cookshoongfrontend.review.model.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 리뷰 수정 dto.
 *
 * @author seungyeon
 * @since 2023.08.16
 */
@Getter
@AllArgsConstructor
public class UpdateReviewResponseDto {
    private Long reviewId;
    @NotBlank
    private String contents;
}
