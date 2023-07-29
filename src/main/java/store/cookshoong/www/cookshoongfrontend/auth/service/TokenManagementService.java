package store.cookshoong.www.cookshoongfrontend.auth.service;

import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import store.cookshoong.www.cookshoongfrontend.auth.adapter.AuthApiAdapter;
import store.cookshoong.www.cookshoongfrontend.auth.entity.RefreshToken;
import store.cookshoong.www.cookshoongfrontend.auth.model.response.AuthenticationResponseDto;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.JwtAuthentication;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.ParsedAccessToken;
import store.cookshoong.www.cookshoongfrontend.auth.repository.RefreshTokenRepository;
import store.cookshoong.www.cookshoongfrontend.auth.util.JwtResolver;
import store.cookshoong.www.cookshoongfrontend.common.util.Times;

/**
 * 토큰관리를 맡고있는 서비스 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.28
 */
@Service
@RequiredArgsConstructor
public class TokenManagementService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthApiAdapter authApiAdapter;


    /**
     * 토큰의 재발급 가능여부를 판단하는 메서드.
     *
     * @param accessToken the access token
     * @return the boolean
     */
    public boolean canReissue(String accessToken) {
        ParsedAccessToken parsedAccessToken = JwtResolver.resolveAccessToken(accessToken);
        Long expiredTime = Long.valueOf(parsedAccessToken.getExp());
        Long now = new Date().toInstant().getEpochSecond();
        return expiredTime - now < 5 * Times.MINUTE && hasRefreshToken(parsedAccessToken.getJti());
    }

    /**
     * 리프레쉬 토큰을 통해 새로운 토큰을 요청하는 메서드.
     *
     * @param accessToken the access token
     */
    public void reissueToken(String accessToken) {
        String jti = JwtResolver.resolveAccessToken(accessToken).getJti();
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findById(jti);
        if (refreshToken.isEmpty()) {
            return;
        }

        RefreshToken oldRefreshToken = refreshToken.get();
        AuthenticationResponseDto authResponse = authApiAdapter.sendRefreshToken(oldRefreshToken.getRawRefreshToken())
            .getBody();
        JwtAuthentication oldAuthentication = (JwtAuthentication) SecurityContextHolder.getContext()
            .getAuthentication();

        Assert.notNull(authResponse, "리프레쉬 토큰 재발급 실패.");
        String newAccessToken = authResponse.getAccessToken();
        String newRefreshToken = authResponse.getRefreshToken();

        oldAuthentication.updateAccessToken(newAccessToken);
        refreshTokenRepository.save(RefreshToken.createRefreshToken(newRefreshToken));
        refreshTokenRepository.deleteById(oldRefreshToken.getJti());
    }

    private boolean hasRefreshToken(String jti) {
        return refreshTokenRepository.existsById(jti);
    }

    /**
     * 해당 토큰을 사용하지 못하는 상태로 만드는 메서드.
     *
     * @param accessToken the access token
     */
    public void invalidate(String accessToken) {
        ParsedAccessToken parsedAccessToken = JwtResolver.resolveAccessToken(accessToken);
        String jti = parsedAccessToken.getJti();
        refreshTokenRepository.deleteById(jti);

        Long expiredTime = Long.valueOf(parsedAccessToken.getExp());
        Long now = new Date().toInstant().getEpochSecond();
        if (expiredTime - now >= Times.MINUTE) {
            authApiAdapter.executeTokenInvalidated(accessToken);
        }
    }

    public void saveRefreshToken(String rawRefreshToken) {
        refreshTokenRepository.save(RefreshToken.createRefreshToken(rawRefreshToken));
    }
}
