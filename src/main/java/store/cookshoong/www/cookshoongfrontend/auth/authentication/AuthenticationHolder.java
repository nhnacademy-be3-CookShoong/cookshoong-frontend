package store.cookshoong.www.cookshoongfrontend.auth.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 인증객체를 다루기 위해 SecurityContext 에서 꺼내는 로직을 추상화시킨 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.08.26
 */
public class AuthenticationHolder {
    private AuthenticationHolder() {}

    public static Authentication getCurrentUser() {
        return getSecurityContext().getAuthentication();
    }

    private static SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }
}
