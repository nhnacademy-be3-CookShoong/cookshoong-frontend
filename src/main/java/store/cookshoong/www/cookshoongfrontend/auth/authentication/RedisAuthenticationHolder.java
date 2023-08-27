package store.cookshoong.www.cookshoongfrontend.auth.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 현재 요청 쓰레드의 SecurityContext와 레디스에 저장된 SecurityContext와 동기화를 위해 작성된 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.08.27
 */
public class RedisAuthenticationHolder {
    private RedisAuthenticationHolder() {}

    /**
     * 변경된 인증객체를 Redis에 반영하는 메서드.
     *
     * @param request        the request
     * @param authentication the authentication
     */
    public static void updateCurrentUser(HttpServletRequest request, Authentication authentication) {
        SecurityContext securityContext = getSecurityContext();
        HttpSession session = request.getSession(false);
        if (session != null && securityContext != null) {
            securityContext.setAuthentication(authentication);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        }
    }

    private static SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }
}
