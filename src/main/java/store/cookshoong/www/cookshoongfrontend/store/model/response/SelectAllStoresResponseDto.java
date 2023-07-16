package store.cookshoong.www.cookshoongfrontend.store.model.response;

import lombok.Getter;

/**
 * 사업자 : 사업자가 운영하고 있는 매장의 리스트 dto.
 *
 * @author seungyeon
 * @since 2023.07.15
 */
@Getter
public class SelectAllStoresResponseDto {
    private Long storeId;
    private String storeName;
    private String storeMainAddress;
    private String storeDetailAddress;
    private String storeStatus;
}
