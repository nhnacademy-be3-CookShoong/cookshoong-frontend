package store.cookshoong.www.cookshoongfrontend.shop.model.request;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 매장 카테고리 수정을 위한 dto.
 *
 * @author seungyeon
 * @since 2023.08.09
 */
@Getter
@AllArgsConstructor
public class UpdateStoreCategoryRequestDto {
    @NotNull
    private List<String> updateStoreCategories;
}
