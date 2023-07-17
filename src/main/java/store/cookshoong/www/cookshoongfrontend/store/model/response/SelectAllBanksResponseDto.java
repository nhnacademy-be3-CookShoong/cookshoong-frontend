package store.cookshoong.www.cookshoongfrontend.store.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사업자, 관리자 : 은행 리스트를 가져오는 Dto.
 *
 * @author seungyeon (유승연)
 * @since 2023.07.14
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectAllBanksResponseDto {
    private String bankTypeCode;
    private String description;
}
