package store.cookshoong.www.cookshoongfrontend.auth.adapter;

import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.ParsedAccessToken;
import store.cookshoong.www.cookshoongfrontend.auth.util.JwtResolver;

/**
 * 토큰으로부터 얻어낸 권한을 Authentication 객체에 맞게 변환해주는 어댑터 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.25
 */
public class JwtRoleAdapter {
    private JwtRoleAdapter() {}

    private static final String PREFIX = "ROLE_";

    public static List<GrantedAuthority> convertToRole(String token) {
        return List.of(new SimpleGrantedAuthority(PREFIX + JwtResolver.resolveToken(token, ParsedAccessToken.class)
            .getAuthority()));
    }
}
