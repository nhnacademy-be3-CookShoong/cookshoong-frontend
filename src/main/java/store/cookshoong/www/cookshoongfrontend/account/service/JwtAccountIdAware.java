package store.cookshoong.www.cookshoongfrontend.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import store.cookshoong.www.cookshoongfrontend.auth.entity.RefreshToken;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.JwtAuthentication;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.ParsedAccessToken;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.ParsedRefreshToken;
import store.cookshoong.www.cookshoongfrontend.auth.repository.RefreshTokenRepository;
import store.cookshoong.www.cookshoongfrontend.common.util.JwtResolver;

/**
 * Json Web Token 으로부터 accountId를 가져오는 방식을 정의하는 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.18
 */
@Component
@RequiredArgsConstructor
public class JwtAccountIdAware implements AccountIdAware {
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public Long getAccountId() {
        RefreshToken refreshToken = refreshTokenRepository.findById(getJti())
            .orElseThrow(() -> new InsufficientAuthenticationException("인증되지 않은 사용자입니다."));
        ParsedRefreshToken parsedRefreshToken = JwtResolver.resolveToken(refreshToken.getRawRefreshToken(),
            ParsedRefreshToken.class);

        return parsedRefreshToken.getAccountId();
    }

    private String getJti() {
        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return JwtResolver.resolveToken(authentication.getName(), ParsedAccessToken.class).getJti();
    }
}
