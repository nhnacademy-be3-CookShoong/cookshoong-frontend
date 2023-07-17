package store.cookshoong.www.cookshoongfrontend.store.model.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 사업자 : 매장 등록 Dto.
 *
 * @author papel
 * @since 2023.07.09
 */
@Getter
@AllArgsConstructor
public class CreateStoreRequestDto {

    private Long merchantId;
    @NotBlank
    private String businessLicenseNumber;
    @NotBlank
    private String representativeName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate openingDate;
    @NotBlank
    private String storeName;
    @NotBlank
    private String mainPlace;
    @NotBlank
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
    private String image;
    @NotNull
    private List<String> storeCategories;
    @NotBlank
    private String bankCode;
    @NotBlank
    private String bankAccount;
}
