package store.cookshoong.www.cookshoongfrontend.auth.hanlder;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import store.cookshoong.www.cookshoongfrontend.auth.service.TokenManagementService;

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
    private final TokenManagementService tokenManagementService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException {
        if (Objects.isNull(authentication)) {
            response.sendRedirect("/login-page");
            return;
        }

        String accessToken = authentication.getName();
        tokenManagementService.invalidate(response, accessToken);
        response.sendRedirect("/login-page");
    }
}
