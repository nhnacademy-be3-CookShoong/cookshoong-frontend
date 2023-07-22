package store.cookshoong.www.cookshoongfrontend.shop.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 키워드에 맞는 매장 응답 Dto.
 *
 * @author papel (윤동현)
 * @since 2023.07.17
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectStoresKeywordSearchResponseDto {
    private Long id;
    private String name;
    private String description;
    private String category;
}
