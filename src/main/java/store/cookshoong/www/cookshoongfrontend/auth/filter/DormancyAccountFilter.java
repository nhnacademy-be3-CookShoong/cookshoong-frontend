package store.cookshoong.www.cookshoongfrontend.auth.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.JwtAuthentication;

/**
 * 휴면 상태인 유저가 다른 페이지에 접근 못하도록 막는 필터.
 *
 * @author koesnam (추만석)
 * @since 2023.08.07
 */
public class DormancyAccountFilter extends OncePerRequestFilter {
    private static final String[] EXCLUDED_PATH = {"/dormancy/**", "/logout", "/login"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(authentication) && authentication instanceof JwtAuthentication
            && ((JwtAuthentication) authentication).getStatus().equals("DORMANCY")) {
            response.sendRedirect("/dormancy");
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        PathMatcher pathMatcher = new AntPathMatcher();
        String requestPath = request.getRequestURI();
        return Arrays.stream(EXCLUDED_PATH)
            .anyMatch(path -> pathMatcher.match(path, requestPath));
    }
}
