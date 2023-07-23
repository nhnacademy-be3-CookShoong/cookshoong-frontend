package store.cookshoong.www.cookshoongfrontend.auth.hanlder;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.ParsedAccessToken;
import store.cookshoong.www.cookshoongfrontend.auth.repository.RefreshTokenRepository;
import store.cookshoong.www.cookshoongfrontend.auth.util.JwtResolver;

/**
 * 로그아웃시 동작들을 정의해줄 핸들러.
 * 1. 리프레쉬토큰이 있다면 지운다.
 * 2. 현재 가지고 있는 액세스토큰을 무효화시킨다.
 *
 * @author koesnam (추만석)
 * @since 2023.07.18
 */
@Component
@RequiredArgsConstructor
public class TokenInvalidationHandler implements LogoutSuccessHandler {
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String accessToken = authentication.getName();
        String jti = JwtResolver.resolveToken(accessToken, ParsedAccessToken.class).getJti();
        refreshTokenRepository.deleteById(jti);
        // TODO : Gateway에 액세스 토큰 만료요청
        // alertInvalidAccessToken(accessToken)
        //

        response.sendRedirect("/login-page");
    }
}
