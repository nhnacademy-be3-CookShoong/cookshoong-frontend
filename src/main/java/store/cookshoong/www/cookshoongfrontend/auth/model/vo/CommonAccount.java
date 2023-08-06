package store.cookshoong.www.cookshoongfrontend.auth.model.vo;

import java.util.Map;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * 기존 인증 객체에서 OAuth2 회원까지 구현된 객체.
 *
 * @author koesnam (추만석)
 * @since 2023.08.01
 */
public class CommonAccount extends JwtAuthentication implements OAuth2User {
    private static final long serialVersionUID = 7145037287817201936L;
    private transient Map<String, Object> attributes;

    /**
     * Instantiates a new CommonAccount.
     *
     * @param accessToken  the access token
     * @param refreshToken the refresh token
     */
    public CommonAccount(String accessToken, String refreshToken) {
        super(accessToken, refreshToken);
    }

    public CommonAccount(String accessToken, String refreshToken, Map<String, Object> attributes) {
        this(accessToken, refreshToken);
        this.attributes = attributes;
    }


    public static CommonAccount authenticated(String accessToken, String refreshToken) {
        return new CommonAccount(accessToken, refreshToken);
    }

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
