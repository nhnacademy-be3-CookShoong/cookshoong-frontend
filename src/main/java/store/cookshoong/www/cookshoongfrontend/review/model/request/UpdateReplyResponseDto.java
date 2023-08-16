package store.cookshoong.www.cookshoongfrontend.review.model.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 사장님 리뷰 답변 dto.
 *
 * @author seungyeon
 * @since 2023.08.16
 */
@Getter
@AllArgsConstructor
public class UpdateReplyResponseDto {
    private Long replyId;
    @NotBlank
    private String replyContents;
}
