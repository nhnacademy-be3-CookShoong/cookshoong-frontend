package store.cookshoong.www.cookshoongfrontend.shop.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 메뉴 그룹 조회 Dto.
 *
 * @author papel (윤동현)
 * @since 2023.07.11
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectMenuGroupResponseDto {
    private Long id;
    private Long storeId;
    private String name;
    private String description;
    private Integer menuSequence;
}
