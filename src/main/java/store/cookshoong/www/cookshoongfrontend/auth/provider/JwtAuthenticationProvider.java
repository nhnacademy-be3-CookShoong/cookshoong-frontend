package store.cookshoong.www.cookshoongfrontend.auth.provider;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.JaasAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import store.cookshoong.www.cookshoongfrontend.account.adapter.AccountApiAdapter;
import store.cookshoong.www.cookshoongfrontend.account.model.response.AccountStatusResponseDto;
import store.cookshoong.www.cookshoongfrontend.auth.adapter.AuthApiAdapter;
import store.cookshoong.www.cookshoongfrontend.auth.model.response.AuthenticationResponseDto;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.JwtAuthentication;
import store.cookshoong.www.cookshoongfrontend.auth.util.JwtResolver;

/**
 * 입력받은 Id와 Pw 기반으로 인증 객체를 생성해주는 인증 공급자 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.14
 */
// TODO: AbstractUserDetailsAuthenticationProvider이 가지활서 기능과 매우 흡사하며 변경가능해 보임.
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final AuthApiAdapter authApiAdapter;
    private final AccountApiAdapter accountApiAdapter;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String loginId = authentication.getName();
        String password = authentication.getCredentials().toString();

        AuthenticationResponseDto authenticationResponseDto;
        try {
            authenticationResponseDto = authApiAdapter.sendAuthentication(loginId, password)
                .getBody();
        } catch (HttpClientErrorException e) {
            throw new BadCredentialsException("아이디 또는 비밀번호를 잘못 입력했습니다.");
        }

        if (authenticationResponseDto == null) {
            throw new AuthenticationServiceException("인증 서버 오류");
        }

        String accessToken = authenticationResponseDto.getAccessToken();
        String refreshToken = authenticationResponseDto.getRefreshToken();

        Long accountId = JwtResolver.resolveRefreshToken(refreshToken).getAccountId();
        AccountStatusResponseDto statusResponseDto = accountApiAdapter.fetchAccountStatus(accountId).getBody();
        Assert.notNull(statusResponseDto, "AccountStatus must not be null!");
        String status = statusResponseDto.getStatus();

        return new JwtAuthentication(accessToken, refreshToken, status);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
