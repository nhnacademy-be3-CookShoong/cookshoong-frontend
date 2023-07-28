package store.cookshoong.www.cookshoongfrontend.cart.model.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotNull
    private Long optionId;
    @NotBlank
    private String optionName;
    @NotNull
    private int optionPrice;
}
