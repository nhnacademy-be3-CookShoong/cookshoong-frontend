package store.cookshoong.www.cookshoongfrontend.order.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 메뉴의 옵션을 확인하는 dto.
 *
 * @author eora21 (김주호)
 * @since 2023.08.10
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectOrderDetailMenuOptionResponseDto {
    private String optionName;
    private int price;
}
