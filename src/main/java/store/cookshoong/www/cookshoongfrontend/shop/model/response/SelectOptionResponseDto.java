package store.cookshoong.www.cookshoongfrontend.shop.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 옵션 조회 Dto.
 *
 * @author papel (윤동현)
 * @since 2023.07.11
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectOptionResponseDto {
    private Long id;
    private Long optionGroupId;
    private String name;
    private Integer price;
    private Boolean isDeleted;
    private Integer optionSequence;
}
