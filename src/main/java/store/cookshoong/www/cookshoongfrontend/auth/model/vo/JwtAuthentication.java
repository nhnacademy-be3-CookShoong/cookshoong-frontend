package store.cookshoong.www.cookshoongfrontend.auth.model.vo;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;
import store.cookshoong.www.cookshoongfrontend.auth.util.CustomAuthorityUtils;
import store.cookshoong.www.cookshoongfrontend.auth.util.JwtResolver;

/**
 * Jwt 을 통한 인증을 담당하는 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.14
 */
public class JwtAuthentication implements Authentication {
    private static final long serialVersionUID = 2899551349677346943L;
    private String accessToken;
    private String refreshToken;
    private String status;
    private Set<GrantedAuthority> authorities;
    private boolean authenticated = false;

    /**
     * Instantiates a new Jwt authentication.
     *
     * @param accessToken the access token
     */
    public JwtAuthentication(String accessToken, String refreshToken) {
        this.authorities = JwtResolver.convertToRole(accessToken);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.status = JwtResolver.convertToStatus(refreshToken);
        this.setAuthenticated(true);
        afterPropertiesSet();
    }

    private void afterPropertiesSet() {
        Assert.notNull(accessToken, "인증된 토큰은 필요합니다.");
        Assert.notNull(refreshToken, "인증 토큰이 발급되었다면 갱신 토큰도 발급됩니다.");
        Assert.notNull(status, "회원 상태는 없을 수 없습니다.");
        if (status.equals("WITHDRAWAL")) {
            throw new DisabledException("탈퇴한 회원입니다.");
        } else if (status.equals("DORMANCY")) {
            this.authorities = CustomAuthorityUtils.createAuthoritySet("ROLE_DORMANCY");
        }
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void eraseRefreshToken() {
        this.refreshToken = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return accessToken;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    public String getStatus() {
        return this.status;
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
        return Objects.hash(accessToken, status, authorities);
    }

    @Override
    public String getName() {
        return (this.getPrincipal() == null) ? "" : this.getPrincipal().toString();
    }

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public void updateAuthorities(String... authorities) {
        this.authorities = CustomAuthorityUtils.createAuthoritySet(authorities);
    }
}
