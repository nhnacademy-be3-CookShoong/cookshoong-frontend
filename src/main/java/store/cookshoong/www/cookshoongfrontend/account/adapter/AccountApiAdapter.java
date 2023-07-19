package store.cookshoong.www.cookshoongfrontend.account.adapter;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.account.model.request.SignUpRequestDto;
import store.cookshoong.www.cookshoongfrontend.common.property.ApiProperties;

/**
 * 회원 관련 API 호출을 담당하는 메서드.
 *
 * @author koesnam (추만석)
 * @since 2023.07.14
 */
@Component
@RequiredArgsConstructor
public class AccountApiAdapter {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    /**
     * BackEnd 서버로 회원등록을 요청하는 메서드.
     *
     * @param authorityCode    the authority code
     * @param signUpRequestDto the sign up request dto
     * @return the response entity
     */
    public ResponseEntity<Void> executeRegistration(String authorityCode, SignUpRequestDto signUpRequestDto) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .path("/api/accounts")
            .queryParam("authorityCode", authorityCode)
            .build()
            .toUri();

        RequestEntity<SignUpRequestDto> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(signUpRequestDto);

        return restTemplate.exchange(request, Void.class);
    }
}
