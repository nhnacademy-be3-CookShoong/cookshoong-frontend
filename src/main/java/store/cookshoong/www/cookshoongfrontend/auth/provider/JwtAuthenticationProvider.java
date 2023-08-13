package store.cookshoong.www.cookshoongfrontend.auth.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
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

        AuthenticationResponseDto authenticationResponseDto = this.authenticate(loginId, password);

        Assert.notNull(authenticationResponseDto, "인증 성공시 토큰을 담은 응답이 없을 수 없습니다.");
        String accessToken = authenticationResponseDto.getAccessToken();
        String refreshToken = authenticationResponseDto.getRefreshToken();
        Assert.notNull(accessToken, "인증 성공시 액세스 토큰이 없을 수 없습니다.");
        Assert.notNull(refreshToken, "인증 성공시 리프레쉬 토큰이 없을 수 없습니다.");
        return new JwtAuthentication(accessToken, refreshToken);
    }

    /**
     * 인증서버로 자격증명을 보내 인증 결과를 받아오는 메서드.
     *
     * @param loginId  the login id
     * @param password the password
     * @return the authentication response dto
     */
    public AuthenticationResponseDto authenticate(String loginId, String password) {
        try {
            return authApiAdapter.sendAuthentication(loginId, password)
                .getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().is4xxClientError()) {
                throw new BadCredentialsException("아이디 또는 비밀번호를 잘못 입력했습니다.");
            }
            throw e;
        } catch (Exception e) {
            throw new AuthenticationServiceException("인증 서버 오류");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
