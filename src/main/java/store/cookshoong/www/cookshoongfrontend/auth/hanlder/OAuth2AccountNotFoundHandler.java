package store.cookshoong.www.cookshoongfrontend.auth.hanlder;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import store.cookshoong.www.cookshoongfrontend.auth.exception.OAuth2AccountNotFoundException;
import store.cookshoong.www.cookshoongfrontend.auth.util.AttributeConverter;

/**
 * OAuth2 로그인을 했으나 기존 회원이 아닐 때 회원가입 페이지로 보내는 핸들러.
 *
 * @author koesnam (추만석)
 * @since 2023.08.03
 */
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
@Component
public class OAuth2AccountNotFoundHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof OAuth2AccountNotFoundException) {
            request.getRequestDispatcher("/sign-up-oauth2").forward(request, response);
        }

        response.sendRedirect("/login-page");
    }
}
