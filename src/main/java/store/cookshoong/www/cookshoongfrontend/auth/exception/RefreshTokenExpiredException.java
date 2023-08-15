package store.cookshoong.www.cookshoongfrontend.auth.exception;

import org.springframework.security.access.AccessDeniedException;

/**
 * 리프레쉬 토큰이 만료됨을 알려주기 위한 예외.
 *
 * @author koesnam (추만석)
 * @since 2023.08.15
 */
public class RefreshTokenExpiredException extends AccessDeniedException {
    private static final long serialVersionUID = 7024647150830860519L;

    public RefreshTokenExpiredException() {
        super("리프레쉬 토큰이 만료되었습니다.");
    }
}
