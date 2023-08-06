package store.cookshoong.www.cookshoongfrontend.coupon.model.vo;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 금액 쿠폰 타입 정보를 담을 vo.
 *
 * @author eora21 (김주호)
 * @since 2023.07.16
 */
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponTypeCashVo implements CouponTypeResponse {
    private int discountAmount;
    private int minimumOrderPrice;

    @Override
    public int getDiscountPrice(int totalPrice) {
        return Math.max(0, totalPrice - discountAmount);
    }
}
