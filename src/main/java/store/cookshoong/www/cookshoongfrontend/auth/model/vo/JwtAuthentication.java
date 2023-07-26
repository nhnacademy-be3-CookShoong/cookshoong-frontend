package store.cookshoong.www.cookshoongfrontend.auth.model.vo;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import store.cookshoong.www.cookshoongfrontend.auth.adapter.JwtRoleAdapter;
import store.cookshoong.www.cookshoongfrontend.auth.util.JwtResolver;

/**
 * Jwt 을 통한 인증을 담당하는 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.14
 */
public class JwtAuthentication extends AbstractAuthenticationToken {
    private final String accessToken;
    private String refreshToken;

    /**
     * Instantiates a new Jwt authentication.
     *
     * @param accessToken the access token
     */
    public JwtAuthentication(String accessToken, String refreshToken) {
        super(JwtRoleAdapter.convertToRole(accessToken));
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.setAuthenticated(true);
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void eraseRefreshToken() {
        this.refreshToken = null;
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
