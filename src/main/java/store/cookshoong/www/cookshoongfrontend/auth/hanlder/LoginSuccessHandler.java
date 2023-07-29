package store.cookshoong.www.cookshoongfrontend.auth.hanlder;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.JwtAuthentication;
import store.cookshoong.www.cookshoongfrontend.auth.service.TokenManagementService;
import store.cookshoong.www.cookshoongfrontend.auth.util.JwtResolver;

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


        // TODO: 인증객체를 UserDetails로 변경 고려!
        // (회원 상태가 필요한데 이를 그냥 불러서 확인할 수 있지만 UserDetails가 가지는 필드들과 매우 유사함.)
        String status = jwtAuthentication.getStatus();
        String redirectPage = "/";
        switch (status) {
            case "DORMANCY":
                redirectPage = "/dormancy";
                break;
            case "WITHDRAWAL":
                jwtAuthentication.setAuthenticated(false);
                throw new DisabledException("탈퇴한 회원입니다");
            case "ACTIVE": default:
                Long accountId = JwtResolver.resolveRefreshToken(refreshToken).getAccountId();
                // TODO: 장바구니 확인
        }

        jwtAuthentication.eraseRefreshToken();
        response.sendRedirect(redirectPage);
    }

}
