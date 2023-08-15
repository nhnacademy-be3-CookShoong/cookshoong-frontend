package store.cookshoong.www.cookshoongfrontend.review.model.request;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사장님이 회원리뷰에 답글을 달 때 필요한 dto.
 *
 * @author seungyeon
 * @since 2023.08.13
 */
@Getter
@AllArgsConstructor
public class CreateBusinessReviewRequestDto {
    @NotBlank
    private String contents;
}
