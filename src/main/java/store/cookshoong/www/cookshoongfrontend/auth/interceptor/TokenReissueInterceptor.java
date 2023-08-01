package store.cookshoong.www.cookshoongfrontend.auth.interceptor;

import java.security.Principal;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import store.cookshoong.www.cookshoongfrontend.auth.service.TokenManagementService;

/**
 * 토큰이 만료되기 전 미리 재발급 받기 위한 인터셉터.
 *
 * @author koesnam (추만석)
 * @since 2023.07.27
 */
@Slf4j
@RequiredArgsConstructor
public class TokenReissueInterceptor implements HandlerInterceptor {
    private final TokenManagementService tokenManagementService;
    private final PathMatcher antMatcher = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Principal principal = request.getUserPrincipal();
        String uri = request.getRequestURI();

        if (Objects.isNull(principal) || antMatcher.match("/images/**", uri)) {
            return true;
        }

        String accessToken = principal.getName();
        if (tokenManagementService.canReissue(accessToken)) {
            tokenManagementService.reissueToken(accessToken);
        }
        return true;
    }
}
