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

    private Long menuId;
    private String menuName;
    private String menuImage;
    private int menuPrice;
}
