package store.cookshoong.www.cookshoongfrontend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.Base64Utils;
import store.cookshoong.www.cookshoongfrontend.auth.exception.InvalidAccessTokenException;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.ParsedAccessToken;

/**
 * Jwt 를 읽기 좋은 형태로 바꿔주는 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.16
 */
public class JwtResolver {
    private JwtResolver() {}

    /**
     * Token 값을 디코딩하여 읽기 좋은 형태로 바꾸는 메서드.
     *
     * @param accessToken String
     * @return the access token
     */
    public static ParsedAccessToken resolveAccessToken(String accessToken) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(resolveToken(accessToken), ParsedAccessToken.class);
        } catch (JsonProcessingException e) {
            throw new InvalidAccessTokenException();
        }
    }

    private static String resolveToken(String token) {
        String payload = token.split("\\.")[1];
        return new String(Base64Utils.decodeFromString(payload));
    }
}
