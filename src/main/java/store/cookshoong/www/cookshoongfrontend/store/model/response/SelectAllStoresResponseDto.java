package store.cookshoong.www.cookshoongfrontend.store.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사업자 : 사업자가 운영하고 있는 매장의 리스트 dto.
 *
 * @author seungyeon
 * @since 2023.07.15
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectAllStoresResponseDto {
    private Long storeId;
    private String storeName;
    private String storeMainAddress;
    private String storeDetailAddress;
    private String storeStatus;
}
