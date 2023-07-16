package store.cookshoong.www.cookshoongfrontend.store.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사업자 : 매장 상태 변경에 사용될 status dto.
 *
 * @author seungyeon
 * @since 2023.07.16
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectAllStatusResponseDto {
    private String statusCode;
    private String statusName;
}
