package store.cookshoong.www.cookshoongfrontend.coupon.model.vo;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 퍼센트 쿠폰 타입 정보를 담을 vo.
 *
 * @author eora21 (김주호)
 * @since 2023.07.16
 */
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponTypePercentVo implements CouponTypeResponse {
    private int rate;
    private int minimumOrderPrice;
    private int maximumDiscountAmount;

    @Override
    public int getDiscountAmount(int totalPrice) {
        return Math.min(totalPrice / 100 * rate, getMaximumDiscountAmount());
    }
}
