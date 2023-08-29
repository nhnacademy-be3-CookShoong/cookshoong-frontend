package store.cookshoong.www.cookshoongfrontend.cart.model.vo;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 장바구니 페이지에서 사용될 Dto.
 *
 * @author papel
 * @since 2023.08.26
 */
@Getter
@NoArgsConstructor
public class CartViewDto {
    private List<Long> optionGroups;

    public CartViewDto(List<Long> optionGroups) {
        this.optionGroups = optionGroups;
    }
}
