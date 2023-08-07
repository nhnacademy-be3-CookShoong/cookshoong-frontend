package store.cookshoong.www.cookshoongfrontend.auth.exception;

import java.util.Map;
import org.springframework.security.core.AuthenticationException;

/**
 * OAuth 로그인시 불충분한 정보를 가지고 있을 때 회원가입 페이지로 보내기 위한 예외.
 *
 * @author koesnam (추만석)
 * @since 2023.08.02
 */
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public final class OAuth2AccountNotFoundException extends AuthenticationException {
    private static final long serialVersionUID = -70824108407794371L;
    private final transient Map<String, Object> attributes;

    public OAuth2AccountNotFoundException(Map<String, Object> attributes) {
        super("미등록된 OAuth2 회원입니다.");
        this.attributes = attributes;
    }

    public Object getAttribute(String field) {
        return this.attributes.get(field);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
