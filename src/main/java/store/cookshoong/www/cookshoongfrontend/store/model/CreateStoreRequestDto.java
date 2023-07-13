package store.cookshoong.www.cookshoongfrontend.store.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 매장 등록 Dto.
 *
 * @author papel
 * @since 2023.07.09
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateStoreRequestDto {
    private String merchantName;
    @NotBlank
    private String businessLicenseNumber;
    @NotBlank
    private String representativeName;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate openingDate;
    @NotBlank
    private String storeName;
    @NotBlank
    private String mainPlace;
    private String detailPlace;
    @NotNull
    private BigDecimal latitude;
    @NotNull
    private BigDecimal longitude;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String businessLicense;
    private String description;
    @NotNull
    private BigDecimal earningRate;
    @NotNull
    private List<String> storeCategories;
    @NotBlank
    private String bankName;
    @NotBlank
    private String bankAccount;
}
