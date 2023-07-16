package store.cookshoong.www.cookshoongfrontend.store.model.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사업자 : 매장 등록 및 수정에 사용될 category dto.
 *
 * @author seungyeon
 * @since 2023.07.13
 */
@Getter
public class SelectAllCategoriesResponseDto {
    private String categoryCode;
    private String categoryName;
}
