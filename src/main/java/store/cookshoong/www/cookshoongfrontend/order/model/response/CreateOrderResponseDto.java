package store.cookshoong.www.cookshoongfrontend.order.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 생성 후 BE에서 검증한 가격을 담는 dto.
 *
 * @author eora21 (김주호)
 * @since 2023.08.04
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateOrderResponseDto {
    private int totalPrice;
}
