package store.cookshoong.www.cookshoongfrontend.auth.filter;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.JwtAuthentication;

/**
 * Jwt를 확인하여 인증된 사용자인지를 검증하는 필터.
 *
 * @author koesnam (추만석)
 * @since 2023.07.14
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final RequestMatcher IS_RESOURCE = new AntPathRequestMatcher("/assets/**");
    private final RequestMatcher IS_LOGIN_PAGE = new AntPathRequestMatcher("/login-page");
    private final RequestMatcher IS_LANDING_PAGE = new AntPathRequestMatcher("/");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
//        if (!Objects.isNull(authentication)) {
//            String accessToken = (String) authentication.getPrincipal();
//            response.setHeader("Authorization", "Bearer " + accessToken);
//        }
        // NOTE: 현재 SecurityContext 에 Jwt(accessToken)가 담겨 있는 상태.
        // NOTE: 그럼 FrontServer의 Security는 무엇을 해야하는가?
        // TODO 1: 토큰이 만료됐는지 확인
        // TODO 1.1: 만료 됐다면 리프레쉬 토큰이 있는지 확인.
        // TODO 1.1.1: 리프레쉬 토큰을 auth에 재전송
        // TODO 1.2: 리프레쉬 토큰도 없다면 Logout 처리.

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return IS_LOGIN_PAGE.matches(request) || IS_RESOURCE.matches(request) || IS_LANDING_PAGE.matches(request);
    }
}
