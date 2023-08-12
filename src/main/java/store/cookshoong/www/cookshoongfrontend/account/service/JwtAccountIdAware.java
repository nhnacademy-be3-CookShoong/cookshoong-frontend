package store.cookshoong.www.cookshoongfrontend.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import store.cookshoong.www.cookshoongfrontend.auth.entity.RefreshToken;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.ParsedRefreshToken;
import store.cookshoong.www.cookshoongfrontend.auth.service.TokenManagementService;
import store.cookshoong.www.cookshoongfrontend.auth.util.JwtResolver;

/**
 * Json Web Token 으로부터 accountId를 가져오는 방식을 정의하는 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.18
 */
@Component
@RequiredArgsConstructor
public class JwtAccountIdAware implements AccountIdAware {
    private final TokenManagementService tokenManagementService;

    @Override
    public Long getAccountId() {
        RefreshToken refreshToken = tokenManagementService.getRefreshToken();
        ParsedRefreshToken parsedRefreshToken = JwtResolver.resolveRefreshToken(refreshToken.getRawRefreshToken());
        return parsedRefreshToken.getAccountId();
    }
}
