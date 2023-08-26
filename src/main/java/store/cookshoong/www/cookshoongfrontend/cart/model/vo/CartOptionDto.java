package store.cookshoong.www.cookshoongfrontend.cart.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 메뉴에 대한 옵션을 담는 Dto.
 *
 * @author jeongjewan
 * @since 2023.07.20
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartOptionDto {

    private Long optionId;
    private String optionName;
    private int optionPrice;
}
