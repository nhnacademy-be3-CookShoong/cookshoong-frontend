package store.cookshoong.www.cookshoongfrontend.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.ParsedRefreshToken;
import store.cookshoong.www.cookshoongfrontend.auth.util.JwtResolver;

/**
 * 리프레쉬 토큰을 레디스에 저장하기 위한 엔터티.
 *
 * @author koesnam (추만석)
 * @since 2023.07.17
 */
@Getter
@RedisHash(value = "refresh_tokens")
@AllArgsConstructor
public class RefreshToken {
    @Id
    private String jti;
    private String rawRefreshToken;
    @TimeToLive
    private Long expireTime;

    /**
     * Redis 에 저장하기 위한 Entity 를 생성하는 메서드.
     *
     * @param rawRefreshToken Jwt 원본 값
     * @return the refresh token
     */
    public static RefreshToken createRefreshToken(String rawRefreshToken) {
        ParsedRefreshToken parsedRefreshToken = JwtResolver.resolveToken(rawRefreshToken, ParsedRefreshToken.class);
        String jti = parsedRefreshToken.getJti();
        Long expireTime = parsedRefreshToken.getExp() - (System.currentTimeMillis() / 1000L);
        return new RefreshToken(jti, rawRefreshToken, expireTime);
    }
}
