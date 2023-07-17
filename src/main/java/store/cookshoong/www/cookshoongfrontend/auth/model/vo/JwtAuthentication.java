package store.cookshoong.www.cookshoongfrontend.auth.model.vo;

import java.util.Collections;
import java.util.Objects;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import store.cookshoong.www.cookshoongfrontend.util.JwtResolver;

/**
 * Jwt 을 통한 인증을 담당하는 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.14
 */
public class JwtAuthentication extends AbstractAuthenticationToken {
    private final String accessToken;

    /**
     * Instantiates a new Jwt authentication.
     *
     * @param accessToken the access token
     */
    public JwtAuthentication(String accessToken) {
        super(Collections.singletonList(new SimpleGrantedAuthority(JwtResolver.resolveAccessToken(accessToken)
            .getAuthority())));
        this.accessToken = accessToken;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return accessToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JwtAuthentication)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        JwtAuthentication that = (JwtAuthentication) o;
        return Objects.equals(accessToken, that.accessToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), accessToken);
    }
}
