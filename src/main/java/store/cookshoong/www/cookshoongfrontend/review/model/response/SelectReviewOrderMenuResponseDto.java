package store.cookshoong.www.cookshoongfrontend.review.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 먹은 메뉴에 대한 이름을 확인하기 위한 dto.
 *
 * @author seungyeon
 * @since 2023.08.13
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectReviewOrderMenuResponseDto {
    private Long menuId;
    private String menuName;
}
