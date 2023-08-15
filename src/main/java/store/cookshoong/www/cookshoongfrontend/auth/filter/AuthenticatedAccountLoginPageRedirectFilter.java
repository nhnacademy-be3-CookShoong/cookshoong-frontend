package store.cookshoong.www.cookshoongfrontend.auth.filter;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.JwtAuthentication;
import store.cookshoong.www.cookshoongfrontend.auth.repository.RefreshTokenManager;

/**
 * 인증된 유저가 로그인 페이지에 진입하는 것을 막기위한 필터.
 *
 * @author koesnam (추만석)
 * @since 2023.08.15
 */
@Component
@RequiredArgsConstructor
public class AuthenticatedAccountLoginPageRedirectFilter extends OncePerRequestFilter {
    private final RefreshTokenManager refreshTokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 인증된 사용자지만 리프레쉬토큰이 없을 때 로그인 화면으로 보내고 있기 때문에, 리프레쉬 토큰의 유무로 로그인 페이지 접근을 막습니다.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (refreshTokenManager.isExists(request) && auth instanceof JwtAuthentication) {
            response.sendRedirect("/");
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getRequestURI().equals("/login-page");
    }
}
