package store.cookshoong.www.cookshoongfrontend.auth.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import store.cookshoong.www.cookshoongfrontend.auth.util.JwtResolver;

/**
 * 리프레쉬 토큰을 레디스에 저장하기 위한 엔터티.
 *
 * @author koesnam (추만석)
 * @since 2023.07.17
 */
@Getter
@AllArgsConstructor
public class RefreshToken {
    private String rawRefreshToken;
    private Long timeToLive;

    /**
     * Redis 에 저장하기 위한 Entity 를 생성하는 메서드.
     *
     * @param rawRefreshToken Jwt 원본 값
     * @return the refresh token
     */
    public static RefreshToken createRefreshToken(String rawRefreshToken) {
        ParsedRefreshToken parsedRefreshToken = JwtResolver.resolveRefreshToken(rawRefreshToken);
        Long timeToLive = parsedRefreshToken.getExp() - (System.currentTimeMillis() / 1000L);
        return new RefreshToken(rawRefreshToken, timeToLive);
    }

    public ParsedRefreshToken parse() {
        return JwtResolver.resolveRefreshToken(this.rawRefreshToken);
    }
}
