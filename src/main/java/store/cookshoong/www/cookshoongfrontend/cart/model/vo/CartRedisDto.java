package store.cookshoong.www.cookshoongfrontend.cart.model.vo;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 장바구니에 저장될 Dto.
 *
 * @author jeongjewan
 * @since 2023.07.20
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartRedisDto {
    @NotNull
    private Long accountId;
    @NotNull
    private Long storeId;
    @NotBlank
    private String storeName;
    @Setter
    private CartMenuDto menu;
    @Setter
    private List<CartOptionDto> options;
    private Long createTimeMillis;
    private String hashKey;
    private Integer count;
    private String menuOptName;
    private String totalMenuPrice;

    /**
     * 메뉴와 옵션을 조합해서 hashKey 를 생성하는 메서드.
     *
     * @return      메뉴 + 오셥s -> hashKey 반환
     */
    public String generateUniqueHashKey() {
        String optionIdsString = options.stream()
            .map(cartOptionDto -> cartOptionDto.getOptionId().toString())
            .collect(Collectors.joining(","));

        return menu.getMenuId() + ":" + optionIdsString;
    }
}
