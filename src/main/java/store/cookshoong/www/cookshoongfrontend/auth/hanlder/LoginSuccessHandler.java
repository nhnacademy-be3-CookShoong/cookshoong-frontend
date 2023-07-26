package store.cookshoong.www.cookshoongfrontend.auth.hanlder;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import store.cookshoong.www.cookshoongfrontend.auth.entity.RefreshToken;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.JwtAuthentication;
import store.cookshoong.www.cookshoongfrontend.auth.repository.RefreshTokenRepository;

/**
 * 로그인 성공시 처리를 해줄 핸들러.
 *
 * @author koesnam (추만석)
 * @since 2023.07.14
 */

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        String rawRefreshToken = jwtAuthentication.getRefreshToken();
        refreshTokenRepository.save(RefreshToken.createRefreshToken(rawRefreshToken));
        jwtAuthentication.eraseRefreshToken();

        goHome(response);
    }

    private void goHome(HttpServletResponse response) throws IOException {
        response.sendRedirect("/");
    }
}
