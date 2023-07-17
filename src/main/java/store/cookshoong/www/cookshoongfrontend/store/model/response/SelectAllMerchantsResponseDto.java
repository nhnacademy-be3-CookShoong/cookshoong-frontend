package store.cookshoong.www.cookshoongfrontend.store.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사업자 : 매장 등록 및 수정에 사용될 merchant dto.
 *
 * @author seungyeon
 * @since 2023.07.14
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectAllMerchantsResponseDto {
    private Long merchantId;
    private String merchantName;
}
