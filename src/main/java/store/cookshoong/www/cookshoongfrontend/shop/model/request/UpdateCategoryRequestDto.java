package store.cookshoong.www.cookshoongfrontend.shop.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 매장 카테고리 수정 dto.
 *
 * @author seungyeon (유승연)
 * @since 2023.07.25
 */
@Getter
@AllArgsConstructor
public class UpdateCategoryRequestDto {
    @NotBlank
    @Size(min = 1, max = 30)
    private String storeCategoryName;
}
