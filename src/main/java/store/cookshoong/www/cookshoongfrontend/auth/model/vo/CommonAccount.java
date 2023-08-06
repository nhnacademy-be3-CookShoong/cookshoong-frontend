package store.cookshoong.www.cookshoongfrontend.auth.model.vo;

import java.util.Map;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * 기존 인증 객체에서 OAuth2 회원까지 구현된 인증 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.08.01
 */
public class CommonAccount extends JwtAuthentication implements OAuth2User {
    private static final long serialVersionUID = 7145037287817201936L;
    private transient Map<String, Object> attributes;

    /**
     * 기존 일반회원 객체(JwtAuthentication)와 동일한 OAuth2 유저 객체를 생성하는 생성자.
     *
     * @param accessToken  the access token
     * @param refreshToken the refresh token
     */
    public CommonAccount(String accessToken, String refreshToken) {
        super(accessToken, refreshToken);
    }


    /**
     * OAuth2 유저로 구분이 가능한 OAuth2 유저 객체를 생성하는 생성자.
     *
     * @param accessToken  the access token
     * @param refreshToken the refresh token
     * @param attributes   the attributes
     */
    public CommonAccount(String accessToken, String refreshToken, Map<String, Object> attributes) {
        this(accessToken, refreshToken);
        this.attributes = attributes;
    }


    /**
     * 인증된(로그인이 성공한) OAuth2 유저 객체를 만들어주는 정적 메서드.
     *
     * @param accessToken  the access token
     * @param refreshToken the refresh token
     * @return the common account
     */
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
