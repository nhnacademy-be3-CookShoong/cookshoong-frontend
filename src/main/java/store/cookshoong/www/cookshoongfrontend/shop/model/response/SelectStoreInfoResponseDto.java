package store.cookshoong.www.cookshoongfrontend.shop.model.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사업자 : 각 매장에 대한 정보 조회 dto.
 *
 * @author seungyeon (유승연)
 * @since 2023.07.19
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectStoreInfoResponseDto {
    private String businessLicenseNumber;
    private String representativeName;
    private LocalDate openingDate;
    private String storeName;
    private String phoneNumber;
    private String mainPlace;
    private String detailPlace;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal defaultEarningRate;
    private String description;
    private String bankCode;
    private String bankAccountNumber;
    private String pathName;
    private String locationType;
    private String domainName;
    private Integer minimumOrderPrice;
    private Integer deliveryCost;
}
