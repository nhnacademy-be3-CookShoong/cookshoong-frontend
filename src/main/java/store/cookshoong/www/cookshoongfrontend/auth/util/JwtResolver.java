package store.cookshoong.www.cookshoongfrontend.auth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Base64Utils;
import store.cookshoong.www.cookshoongfrontend.auth.exception.InvalidAccessTokenException;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.ParsedAccessToken;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.ParsedRefreshToken;

/**
 * Jwt 를 읽기 좋은 형태로 바꿔주는 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.16
 */
public class JwtResolver {
    private JwtResolver() {
    }


    public static ParsedAccessToken resolveAccessToken(String accessToken) {
        return resolveToken(accessToken, ParsedAccessToken.class);
    }

    public static ParsedRefreshToken resolveRefreshToken(String refreshToken) {
        return resolveToken(refreshToken, ParsedRefreshToken.class);
    }

    /**
     * Token 값을 디코딩하여 읽기 좋은 형태로 바꾸는 메서드.
     *
     * @param <T>       the type parameter
     * @param token     the token
     * @param tokenType the token type
     * @return the access token
     */
    public static <T> T resolveToken(String token, Class<T> tokenType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(resolveToken(token), tokenType);
        } catch (JsonProcessingException e) {
            throw new InvalidAccessTokenException();
        }
    }

    private static String resolveToken(String token) {
        String payload = token.split("\\.")[1];
        return new String(Base64Utils.decodeFromString(payload));
    }

    /**
     * 토큰으로부터 받아온 권한을 시큐리티에 호환되는 형식의 권한으로 바꿔준다.
     *
     * @param token the token
     * @return the list
     */
    public static Set<GrantedAuthority> obtainRole(String token) {
        return CustomAuthorityUtils.createAuthoritySet("ROLE_" + resolveAccessToken(token).getAuthority());
    }

    public static String obtainStatus(String refreshToken) {
        return resolveRefreshToken(refreshToken).getStatus();
    }
}
