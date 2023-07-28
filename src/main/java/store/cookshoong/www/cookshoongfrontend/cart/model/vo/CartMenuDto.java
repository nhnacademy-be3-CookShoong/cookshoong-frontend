package store.cookshoong.www.cookshoongfrontend.cart.model.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 메뉴를 담는 Dto.
 *
 * @author jeongjewan
 * @since 2023.07.20
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartMenuDto {

    @NotNull
    private Long menuId;
    @NotBlank
    private String menuName;
    @NotBlank
    private String menuImage;
    @NotNull
    private int menuPrice;
}
