package store.cookshoong.www.cookshoongfrontend.shop.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 옵션 그룹 조회 Dto.
 *
 * @author papel (윤동현)
 * @since 2023.07.11
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectOptionGroupResponseDto {
    private Long id;
    private Long storeId;
    private String name;
    private Integer minSelectCount;
    private Integer maxSelectCount;
    private Boolean isDeleted;
}
