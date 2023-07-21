package store.cookshoong.www.cookshoongfrontend.coupon.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.cookshoong.www.cookshoongfrontend.coupon.model.vo.CouponTypeResponse;

/**
 * 쿠폰 정책을 확인하기 위한 dto.
 *
 * @author eora21 (김주호)
 * @since 2023.07.15
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectPolicyResponseDto {
    private Long id;
    private CouponTypeResponse couponTypeResponse;
    private String name;
    private String description;
    private Integer usagePeriod;
    private Long unclaimedCouponCount;
    private Long issueCouponCount;
}
