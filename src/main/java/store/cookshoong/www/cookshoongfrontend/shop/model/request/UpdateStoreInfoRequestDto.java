package store.cookshoong.www.cookshoongfrontend.shop.model.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import store.cookshoong.www.cookshoongfrontend.common.model.vo.RegularExpressions;
import store.cookshoong.www.cookshoongfrontend.common.model.vo.ValidationFailureMessages;

/**
 * 사업자 : 매장 정보 수정 dto (영업점 정보 수정).
 *
 * @author seungyeon
 * @since 2023.08.09
 */
@Getter
@AllArgsConstructor
public class UpdateStoreInfoRequestDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate openingDate;
    @NotBlank
    private String storeName;
    @NotBlank
    @Pattern(regexp = RegularExpressions.MAIN_DETAIL_ADDRESS, message = ValidationFailureMessages.MAIN_DETAIL_ADDRESS)
    private String mainPlace;
    @NotBlank
    @Pattern(regexp = RegularExpressions.MAIN_DETAIL_ADDRESS, message = ValidationFailureMessages.MAIN_DETAIL_ADDRESS)
    private String detailPlace;
    @NotNull
    private BigDecimal latitude;
    @NotNull
    private BigDecimal longitude;
    @NotBlank
    @Pattern(regexp = RegularExpressions.NUMBER_ONLY, message = ValidationFailureMessages.NUMBER_ONLY)
    private String phoneNumber;

    private String description;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "9.9")
    @NotNull
    private BigDecimal earningRate;

    @Min(0)
    @NotNull
    private Integer minimumOrderPrice;

    @Min(4000)
    @NotNull
    private Integer deliveryCost;
}
