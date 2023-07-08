package store.cookshoong.www.cookshoongfrontend.account.service;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.account.model.request.SignUpRequestDto;
import store.cookshoong.www.cookshoongfrontend.account.exception.RegisterFailureException;
import store.cookshoong.www.cookshoongfrontend.common.config.ApiProperties;

/**
 * 회원 정보를 다루는 API 를 호출하기 위한 서비스 클래스.
 *
 * @author koesnam
 * @since 2023.07.08
 */
@Service
@RequiredArgsConstructor
public class AccountService {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    /**
     * API 서버에 회원가입 요청을 보낸다.
     *
     * @param userType         회원 종류
     * @param signUpRequestDto 회원가입 요청 정보
     */
    public void requestAccountRegistration(String userType, SignUpRequestDto signUpRequestDto) {
        String authorityCode = userType.equals("cus") ? "customer" : "business";

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getBaseUrl())
            .path("/api/accounts")
            .queryParam("authorityCode", authorityCode)
            .encode()
            .build()
            .toUri();

        RequestEntity<SignUpRequestDto> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(signUpRequestDto);

        ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);

        // TODO : 예외처리가 필요함.
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RegisterFailureException(response.getStatusCode());
        }
        // ISSUE: 중복된 아이디일 경우 저장만 안되고 계속 페이지를 머움
    }
}
