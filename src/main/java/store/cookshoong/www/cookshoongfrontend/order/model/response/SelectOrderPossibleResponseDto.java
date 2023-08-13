package store.cookshoong.www.cookshoongfrontend.order.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 가능 여부 반환 dto.
 *
 * @author eora21 (김주호)
 * @since 2023.08.12
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectOrderPossibleResponseDto {
    boolean orderPossible;
    String explain;
}
