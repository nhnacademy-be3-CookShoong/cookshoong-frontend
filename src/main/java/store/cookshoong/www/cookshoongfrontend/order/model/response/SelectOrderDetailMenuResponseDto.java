package store.cookshoong.www.cookshoongfrontend.order.model.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 메뉴 정보를 확인하는 dto.
 *
 * @author eora21 (김주호)
 * @since 2023.08.10
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectOrderDetailMenuResponseDto {
    private Long orderDetailId;
    private String menuName;
    private int cookingTime;
    private int count;
    private int cost;
    private List<SelectOrderDetailMenuOptionResponseDto> selectOrderDetailMenuOptions;
    private int totalCost;
}
