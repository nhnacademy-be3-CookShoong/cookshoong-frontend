package store.cookshoong.www.cookshoongfrontend.auth.hanlder;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import store.cookshoong.www.cookshoongfrontend.auth.exception.RefreshTokenExpiredException;

/**
 * 접근 불가능한 권한들을 제어하기 위한 핸듣러.
 *
 * @author koesnam (추만석)
 * @since 2023.08.14
 */
@Component
public class ForbiddenAccessHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (accessDeniedException instanceof RefreshTokenExpiredException) {
            response.sendRedirect("/login-page");
            return;
        }
        response.sendError(403);
    }
}
