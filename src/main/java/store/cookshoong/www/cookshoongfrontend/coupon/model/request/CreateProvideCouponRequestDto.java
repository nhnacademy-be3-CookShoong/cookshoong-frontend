package store.cookshoong.www.cookshoongfrontend.coupon.model.request;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 쿠폰 발급 요청 시 사용되는 dto.
 *
 * @author eora21 (김주호)
 * @since 2023.07.25
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateProvideCouponRequestDto {
    @NotNull
    private Long couponPolicyId;
    @Setter
    private Long accountId;
}
