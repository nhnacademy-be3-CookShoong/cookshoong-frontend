package store.cookshoong.www.cookshoongfrontend.order.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유저 주문 기록 dto.
 *
 * @author eora21 (김주호)
 * @since 2023.08.13
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectAccountOrderInStatusResponseDto {
    private SelectOrderInStatusResponseDto lookupOrderInStatusResponseDto;
    private String storeName;
    private Integer couponDiscountAmount;
    private Integer pointDiscountAmount;
    private Integer deliveryCost;
    private int totalOrderPrice;
    private boolean writableReview;
}
