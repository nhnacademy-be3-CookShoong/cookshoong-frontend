package store.cookshoong.www.cookshoongfrontend.cart.model.vo;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 장바구니 페이지에서 사용될 Dto.
 *
 * @author papel
 * @since 2023.08.26
 */
@Getter
@Builder
@NoArgsConstructor
public class CartViewDto {
    private Long accountId;
    @NotNull
    private Long storeId;
    @NotBlank
    private String storeName;
    @NotNull
    private Integer deliveryCost;
    @NotNull
    private Integer minimumOrderPrice;
    @Valid
    private CartMenuDto menu;
    private List<CartOptionDto> options;
    private Long createTimeMillis;
    private String hashKey;
    private Integer count;
    private String menuOptName;
    private String totalMenuPrice;
    private List<Long> optionGroups;

    public CartViewDto(Long accountId, Long storeId, String storeName, Integer deliveryCost, Integer minimumOrderPrice, CartMenuDto menu, List<CartOptionDto> options, Long createTimeMillis, String hashKey, Integer count, String menuOptName, String totalMenuPrice, List<Long> optionGroups) {
        this.accountId = accountId;
        this.storeId = storeId;
        this.storeName = storeName;
        this.deliveryCost = deliveryCost;
        this.minimumOrderPrice = minimumOrderPrice;
        this.menu = menu;
        this.options = options;
        this.createTimeMillis = createTimeMillis;
        this.hashKey = hashKey;
        this.count = count;
        this.menuOptName = menuOptName;
        this.totalMenuPrice = totalMenuPrice;
        this.optionGroups = optionGroups;
    }
}
