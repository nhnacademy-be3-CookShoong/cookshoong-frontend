package store.cookshoong.www.cookshoongfrontend.coupon.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 쿠폰 발급 요청 시 사용되는 dto.
 *
 * @author eora21 (김주호)
 * @since 2023.07.17
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateIssueCouponRequestDto {
    Long issueQuantity;
    Long couponPolicyId;
}
