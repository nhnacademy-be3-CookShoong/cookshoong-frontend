package store.cookshoong.www.cookshoongfrontend.auth.adapter;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.auth.model.request.LoginRequestDto;
import store.cookshoong.www.cookshoongfrontend.auth.model.response.AuthenticationResponseDto;
import store.cookshoong.www.cookshoongfrontend.common.property.ApiProperties;

/**
 * Auth 관련된 API 호출을 하기 위한 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.14
 */
@Component
@RequiredArgsConstructor
public class AuthApiAdapter {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    /**
     * 자격 증명 정보를 Auth 서버로 보내 회원 정보를 가져오는 메서드.
     *
     * @param loginId  the login id
     * @param password the password
     * @return the response entity
     */
    public ResponseEntity<AuthenticationResponseDto> sendAuthentication(String loginId, String password) {
        LoginRequestDto loginRequestDto = new LoginRequestDto(loginId, password);

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("auth")
            .pathSegment("login")
            .build()
            .toUri();

        HttpEntity<LoginRequestDto> body = new HttpEntity<>(loginRequestDto);
        return restTemplate.exchange(uri, HttpMethod.POST, body, new ParameterizedTypeReference<>() {});
    }

    /**
     * 게이트웨이에 무효화시킬 토큰을 보내는 메서드.
     *
     */
    public void executeTokenInvalidated() {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("token-invalidate")
            .build()
            .toUri();
        restTemplate.exchange(uri, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<>() {});
    }
}
