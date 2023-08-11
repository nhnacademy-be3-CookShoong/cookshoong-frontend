package store.cookshoong.www.cookshoongfrontend.address.model.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상세 주소를 응답해 주는 Dto.
 *
 * @author jeongjewan
 * @since 2023.07.04
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountAddressResponseDto {

    private Long id;
    private String alias;
    private String mainPlace;
    private String detailPlace;
}
