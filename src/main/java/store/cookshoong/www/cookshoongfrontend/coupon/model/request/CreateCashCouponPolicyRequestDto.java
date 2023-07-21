package store.cookshoong.www.cookshoongfrontend.coupon.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 가게에서 금액 쿠폰 정책을 생성할 때 사용되는 dto.
 *
 * @author eora21 (김주호)
 * @since 2023.07.04
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateCashCouponPolicyRequestDto {
    @NotBlank
    @Length(max = 20)
    private String name;

    @NotBlank
    @Length(max = 50)
    private String description;

    @NotNull
    private Integer usagePeriod;

    @NotNull
    @Min(value = 1_000)
    @Max(value = 50_000)
    private Integer discountAmount;

    @NotNull
    @Min(value = 0)
    @Max(value = 20_000)
    private Integer minimumOrderPrice;
}

