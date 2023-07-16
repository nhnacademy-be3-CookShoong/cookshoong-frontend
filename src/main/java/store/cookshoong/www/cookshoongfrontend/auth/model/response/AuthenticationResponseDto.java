package store.cookshoong.www.cookshoongfrontend.auth.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Auth 서버를 통해 인증완료 후 받아올 객체.
 *
 * @author koesnam (추만석)
 * @since 2023.07.13
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthenticationResponseDto {
    private String accessToken; // uuid와 권한이 담겨있음. (만료시간)
    private String refreshToken; // accountId, status,
}
