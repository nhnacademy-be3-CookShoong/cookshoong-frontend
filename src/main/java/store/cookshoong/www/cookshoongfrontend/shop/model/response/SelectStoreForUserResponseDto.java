package store.cookshoong.www.cookshoongfrontend.shop.model.response;

import java.time.LocalDate;
import lombok.Getter;

/**
 * 매장 조회 Dto.
 *
 * @author papel
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
}
