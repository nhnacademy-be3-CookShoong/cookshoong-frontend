package store.cookshoong.www.cookshoongfrontend.account.adapter;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.account.model.request.OAuth2SignUpRequestDto;
import store.cookshoong.www.cookshoongfrontend.account.model.request.SignUpRequestDto;
import store.cookshoong.www.cookshoongfrontend.account.model.request.UpdateAccountInfoRequestDto;
import store.cookshoong.www.cookshoongfrontend.account.model.response.AccountInfoResponseDto;
import store.cookshoong.www.cookshoongfrontend.account.model.response.AccountStatusResponseDto;
import store.cookshoong.www.cookshoongfrontend.account.model.response.UpdateAccountStatusResponseDto;
import store.cookshoong.www.cookshoongfrontend.account.model.vo.AccountMyPageInfo;
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
    private static final String ACCOUNT_API_PREFIX = "/api/accounts";

    /**
     * BackEnd 서버로 회원등록을 요청하는 메서드.
     *
     * @param authorityCode    the authority code
     * @param signUpRequestDto the sign up request dto
     * @return the response entity
     */
    public ResponseEntity<Void> executeAccountRegistration(String authorityCode, SignUpRequestDto signUpRequestDto) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .path(ACCOUNT_API_PREFIX)
            .queryParam("authorityCode", authorityCode)
            .build()
            .toUri();

        RequestEntity<SignUpRequestDto> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(signUpRequestDto);

        return restTemplate.exchange(request, Void.class);
    }

    /**
     * BackEnd 서버로 OAuth2 회원등록을 요청하는 메서드.
     *
     * @param oAuth2SignUpRequestDto the oauth2 sign up request dto
     * @return the response entity
     */
    public ResponseEntity<Void> executeOAuth2AccountRegistration(OAuth2SignUpRequestDto oAuth2SignUpRequestDto) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .path(ACCOUNT_API_PREFIX)
            .pathSegment("oauth2")
            .build()
            .toUri();

        RequestEntity<OAuth2SignUpRequestDto> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(oAuth2SignUpRequestDto);

        return restTemplate.exchange(request, Void.class);
    }

    /**
     * 회원 상태를 조회하는 메서드.
     *
     * @param accountId the account id
     * @return the response entity
     */
    public ResponseEntity<AccountStatusResponseDto> fetchAccountStatus(Long accountId) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .path(ACCOUNT_API_PREFIX)
            .pathSegment("{accountId}")
            .pathSegment("status")
            .buildAndExpand(accountId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .build();

        return restTemplate.exchange(request, AccountStatusResponseDto.class);
    }

    /**
     * 회원상태를 변경하는 메서드.
     *
     * @param accountId  the account id
     * @param statusCode the status code
     * @return the response entity
     */
    public ResponseEntity<UpdateAccountStatusResponseDto> executeChangeStatus(Long accountId, String statusCode) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .path(ACCOUNT_API_PREFIX)
            .pathSegment("{accountId}")
            .pathSegment("status")
            .queryParam("code", statusCode)
            .buildAndExpand(accountId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.put(uri)
            .build();

        return restTemplate.exchange(request, UpdateAccountStatusResponseDto.class);
    }


    /**
     * loginId를 통해 회원존재여부를 확인하는 메서드.
     *
     * @param loginId the login id
     * @return the response entity
     */
    public ResponseEntity<Void> fetchAccountExistence(String loginId) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .path(ACCOUNT_API_PREFIX)
            .pathSegment("login-id-exists")
            .pathSegment("{loginId}")
            .buildAndExpand(loginId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .build();

        return restTemplate.exchange(request, Void.class);
    }

    /**
     * 마이페이지에서 보이는 정보에 대한 수정을 요청하는 메서드.
     *
     * @param accountId                   the account id
     * @param updateAccountInfoRequestDto the update account info request dto
     */
    public void executeChangeMyPageInfo(Long accountId, UpdateAccountInfoRequestDto updateAccountInfoRequestDto) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .path(ACCOUNT_API_PREFIX)
            .pathSegment("{accountId}")
            .buildAndExpand(accountId)
            .toUri();

        RequestEntity<UpdateAccountInfoRequestDto> request = RequestEntity.put(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(updateAccountInfoRequestDto);

        restTemplate.exchange(request, Void.class);
    }

    /**
     * 회원의 모든 정보에 대한 조회를 요청하는 메서드.
     *
     * @param accountId the account id
     * @return the account my page info
     */
    public ResponseEntity<AccountInfoResponseDto> fetchAccountMyPageInfo(Long accountId) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .path(ACCOUNT_API_PREFIX)
            .pathSegment("{accountId}")
            .buildAndExpand(accountId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .build();

        return restTemplate.exchange(request, AccountInfoResponseDto.class);
    }
}
