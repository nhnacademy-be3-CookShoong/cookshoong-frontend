package store.cookshoong.www.cookshoongfrontend.auth.hanlder;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * OAuth2 로그인 성공시 인증 객체를 OAuth2AuthenticationToken 이 아닌 CommonUser 로 사용하게 변환시켜 주는 핸들러.
 * OAuth2AuthenticationToken 에서 CommonUser 를 꺼내와서 시큐리티 컨텍스트에 담는다.
 * 이를 통해 인증 객체에 대한 정보를 얻어올 때 한번에 꺼내 쓸 수 있다.
 *
 * @author koesnam (추만석)
 * @since 2023.08.01
 */
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final LoginSuccessHandler loginSuccessHandler;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        SecurityContextHolder.getContext().setAuthentication((Authentication) authentication.getPrincipal());
        loginSuccessHandler.onAuthenticationSuccess(request, response, (Authentication) authentication.getPrincipal());
    }
}
