package store.cookshoong.www.cookshoongfrontend.coupon.model.response;

import java.time.LocalDate;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.cookshoong.www.cookshoongfrontend.coupon.model.vo.CouponTypeResponse;

/**
 * 소유한 쿠폰 dto.
 *
 * @author eora21 (김주호)
 * @since 2023.08.05
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectOwnCouponResponseDto {
    private UUID issueCouponCode;
    private CouponTypeResponse couponTypeResponse;
    private String couponUsageName;
    private String name;
    private String description;
    private LocalDate expirationDate;
    private String logTypeDescription;
}
