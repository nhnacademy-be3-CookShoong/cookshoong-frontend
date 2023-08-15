package store.cookshoong.www.cookshoongfrontend.coupon.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.cookshoong.www.cookshoongfrontend.coupon.model.vo.CouponTypeResponse;

/**
 * 쿠폰 발급을 위해 정책 id와 쿠폰 타입, 쿠폰 사용 기간을 전달하는 dto.
 *
 * @author eora21 (김주호)
 * @since 2023.07.24
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectProvableCouponPolicyResponseDto {
    private Long couponPolicyId;
    private CouponTypeResponse couponTypeResponse;
    private Integer usagePeriod;
}
