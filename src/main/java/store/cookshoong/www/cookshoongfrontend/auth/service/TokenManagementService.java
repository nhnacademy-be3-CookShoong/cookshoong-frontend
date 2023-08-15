package store.cookshoong.www.cookshoongfrontend.auth.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import store.cookshoong.www.cookshoongfrontend.auth.adapter.AuthApiAdapter;
import store.cookshoong.www.cookshoongfrontend.auth.exception.RefreshTokenExpiredException;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.RefreshToken;
import store.cookshoong.www.cookshoongfrontend.auth.model.response.AuthenticationResponseDto;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.ParsedAccessToken;
import store.cookshoong.www.cookshoongfrontend.auth.repository.RefreshTokenManager;
import store.cookshoong.www.cookshoongfrontend.auth.util.JwtResolver;
import store.cookshoong.www.cookshoongfrontend.common.util.Times;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 토큰관리를 맡고있는 서비스 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.28
 */
@Service
@RequiredArgsConstructor
public class TokenManagementService {
    private final RefreshTokenManager refreshTokenManager;
    private final AuthApiAdapter authApiAdapter;

    /**
     * 토큰의 재발급 가능여부를 판단하는 메서드.
     *
     * @param accessToken the access token
     * @return the boolean
     */
    public boolean isTimeToLiveUnderFiveMinutes(String accessToken) {
        ParsedAccessToken parsedAccessToken = JwtResolver.resolveAccessToken(accessToken);
        Long expiredTime = Long.valueOf(parsedAccessToken.getExp());
        Long now = new Date().toInstant().getEpochSecond();
        int ttl = (int) (expiredTime - now);
        return ttl <= 5 * Times.MINUTE;
    }

    /**
     * 리프레쉬 토큰을 통해 새로운 토큰을 요청하는 메서드.
     */
    public AuthenticationResponseDto reissueToken() {
        RefreshToken refreshToken = getRefreshToken();
        if (Objects.isNull(refreshToken)) {
            throw new RefreshTokenExpiredException();
        }
        return sendRefreshTokenForRenewal(refreshToken);
    }

    private AuthenticationResponseDto sendRefreshTokenForRenewal(RefreshToken refreshToken) {
        return authApiAdapter.executeTokenRenewal(refreshToken.getRawRefreshToken())
            .getBody();
    }

    /**
     * 해당 토큰을 사용하지 못하는 상태로 만드는 메서드.
     *
     * @param accessToken the access token
     */
    public void invalidate(HttpServletResponse response, String accessToken) {
        refreshTokenManager.delete(response);
        authApiAdapter.executeTokenInvalidated(accessToken);
    }

    public void saveRefreshToken(HttpServletResponse response, String refreshToken) {
        Assert.notNull(refreshToken, "리프레쉬토큰 값이 없으면 안됩니다.");
        refreshTokenManager.save(response, RefreshToken.createRefreshToken(refreshToken));
    }

    public boolean hasRefreshToken(HttpServletRequest request) {
        return refreshTokenManager.isExists(request);
    }

    public RefreshToken getRefreshToken() {
        return refreshTokenManager.getCurrentRefreshToken();
    }
}
