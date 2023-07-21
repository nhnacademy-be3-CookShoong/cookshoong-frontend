package store.cookshoong.www.cookshoongfrontend.auth.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import store.cookshoong.www.cookshoongfrontend.auth.adapter.AuthApiAdapter;
import store.cookshoong.www.cookshoongfrontend.auth.model.response.AuthenticationResponseDto;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.JwtAuthentication;

/**
 * 입력받은 Id와 Pw 기반으로 인증 객체를 생성해주는 인증 공급자 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.14
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final AuthApiAdapter authApiAdapter;

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

        assert authenticationResponseDto != null;
        String accessToken = authenticationResponseDto.getAccessToken();
        String refreshToken = authenticationResponseDto.getRefreshToken();
        return new JwtAuthentication(accessToken, refreshToken);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
