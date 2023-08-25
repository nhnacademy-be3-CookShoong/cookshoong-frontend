package store.cookshoong.www.cookshoongfrontend.auth.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import store.cookshoong.www.cookshoongfrontend.auth.adapter.AuthApiAdapter;
import store.cookshoong.www.cookshoongfrontend.auth.authentication.JwtAuthentication;
import store.cookshoong.www.cookshoongfrontend.auth.model.response.AuthenticationResponseDto;
import store.cookshoong.www.cookshoongfrontend.common.util.ExceptionUtils;

/**
 * 입력받은 Id와 Pw 기반으로 인증 객체를 생성해주는 인증 공급자 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.14
 */
@Slf4j
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
        return createJwtAuthentication(accessToken, refreshToken);
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
            return authApiAdapter.sendAuthentication(loginId, password).getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new BadCredentialsException("아이디 또는 비밀번호를 잘못 입력했습니다.");
            }
            log.error("일반 로그인 과정 중 인증서버에서 실패 \n\n상태코드 : {}, 에러메세지 : {}", e.getRawStatusCode(), e.getMessage());
            throw e;
        } catch (HttpServerErrorException e2) {
            log.error("인증 서버 내부 오류 발생");
            throw new AuthenticationServiceException("인증 서버 통신 실패");
        } catch (Exception e3) {
            log.error("일반 로그인 과정 중 프론트서버에서 실패 \n\n발생한 예외 : {} , 에러메세지 : {} \n\n --> {}", e3.getClass(),
                e3.getMessage(), ExceptionUtils.getStackTrace(e3));
            throw new AuthenticationServiceException("인증 서버 송신 실패");
        }
    }

    private Authentication createJwtAuthentication(String accessToken, String refreshToken) {
        Assert.notNull(accessToken, "액세스 토큰없이 인증 객체를 생성할 수 없습니다.");
        Assert.notNull(refreshToken, "리프레쉬 토큰없이 인증 객체를 생성할 수 없습니다.");
        return new JwtAuthentication(accessToken, refreshToken);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
