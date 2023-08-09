package store.cookshoong.www.cookshoongfrontend.shop.model.response;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import store.cookshoong.www.cookshoongfrontend.coupon.model.response.SelectProvableStoreCouponPolicyResponseDto;

/**
 * 매장 조회 Dto.
 *
 * @author papel (윤동현)
 * @contributor eora21 (김주호)
 * @since 2023.07.18
 */
@Getter
public class SelectStoreForUserResponseDto {
    private String businessLicenseNumber;
    private String representativeName;
    private LocalDate openingDate;
    private String storeName;
    private String phoneNumber;
    private String mainPlace;
    private String detailPlace;
    private String description;
    private Integer minimumOrderPrice;
    private Integer deliveryCost;
    private String locationType;
    private String domainName;
    private String savedName;
    private List<SelectProvableStoreCouponPolicyResponseDto> provableCouponPolicies;
    private String storeStatus;
    private Integer distance;
    private Integer totalDeliveryCost;
    private Integer deliveryTime;
}
