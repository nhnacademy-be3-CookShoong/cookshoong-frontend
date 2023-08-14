package store.cookshoong.www.cookshoongfrontend.review.model.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 사용자 리뷰 작성.
 * 각 주문 내역 중 COMPLETE 상태의 주문은 리뷰를 작성할 수 있음.
 *
 * @author seungyeon
 * @since 2023.08.13
 */
@Getter
@AllArgsConstructor
public class CreateReviewRequestDto {
    @NotBlank
    private String contents;
    private Integer rating;
}
