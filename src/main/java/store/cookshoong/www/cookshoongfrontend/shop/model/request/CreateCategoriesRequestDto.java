package store.cookshoong.www.cookshoongfrontend.shop.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 카테고리 등록 dto.
 *
 * @author seungyeon (유승연)
 * @since 2023.07.25
 */
@Getter
@AllArgsConstructor
public class CreateCategoriesRequestDto {
    @NotBlank
    @Size(min = 1, max = 10)
    private String storeCategoryCode;
    @NotBlank
    @Size(min = 1, max = 10)
    private String storeCategoryName;
}
