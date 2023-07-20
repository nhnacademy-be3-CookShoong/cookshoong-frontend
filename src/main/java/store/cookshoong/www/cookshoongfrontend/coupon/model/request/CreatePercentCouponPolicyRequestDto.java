package store.cookshoong.www.cookshoongfrontend.coupon.model.request;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 포인트 쿠폰 정책을 생성할 때 사용되는 dto.
 *
 * @author eora21
 * @since 2023.07.04
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatePercentCouponPolicyRequestDto {
    @NotBlank
    @Length(max = 20)
    private String name;

    @NotBlank
    @Length(max = 50)
    private String description;

    @NotNull
    private Integer usagePeriod;

    @NotNull
    @DecimalMin(value = "10.0")
    @DecimalMax(value = "50.0")
    @Digits(integer = 3, fraction = 1)
    private BigDecimal rate;

    @NotNull
    @Min(value = 0)
    @Max(value = 20_000)
    private Integer minimumOrderPrice;

    @NotNull
    @Min(value = 5_000)
    @Max(value = 50_000)
    private Integer maximumDiscountAmount;
}

