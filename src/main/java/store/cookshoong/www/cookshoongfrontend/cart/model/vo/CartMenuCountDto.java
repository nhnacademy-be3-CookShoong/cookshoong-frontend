package store.cookshoong.www.cookshoongfrontend.cart.model.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Redis 장바구니에 담기는 수에 대한 Dto.
 *
 * @author jeongjewan
 * @since 2023.07.23
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartMenuCountDto {
    private Long count;
}
