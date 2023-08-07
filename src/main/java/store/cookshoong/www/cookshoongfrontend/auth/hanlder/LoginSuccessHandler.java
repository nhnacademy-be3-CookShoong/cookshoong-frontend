package store.cookshoong.www.cookshoongfrontend.auth.hanlder;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.JwtAuthentication;
import store.cookshoong.www.cookshoongfrontend.auth.service.TokenManagementService;

/**
 * 로그인 성공시 처리를 해줄 핸들러.
 *
 * @author koesnam (추만석)
 * @since 2023.07.14
 */

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final TokenManagementService tokenManagementService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        String refreshToken = jwtAuthentication.getRefreshToken();
        tokenManagementService.saveRefreshToken(refreshToken);
        jwtAuthentication.eraseRefreshToken();

        if (jwtAuthentication.getStatus().equals("DORMANCY")) {
            response.sendRedirect("/dormancy");
        }
        response.sendRedirect("/");
    }

}
