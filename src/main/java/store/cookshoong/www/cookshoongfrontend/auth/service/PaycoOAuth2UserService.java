package store.cookshoong.www.cookshoongfrontend.auth.service;


import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import store.cookshoong.www.cookshoongfrontend.auth.adapter.AuthApiAdapter;
import store.cookshoong.www.cookshoongfrontend.auth.authentication.CommonAccount;
import store.cookshoong.www.cookshoongfrontend.auth.exception.OAuth2AccountNotFoundException;
import store.cookshoong.www.cookshoongfrontend.auth.model.response.AuthenticationResponseDto;
import store.cookshoong.www.cookshoongfrontend.auth.model.response.PaycoAuthenticationResponseDto;
import store.cookshoong.www.cookshoongfrontend.common.util.ExceptionUtils;

/**
 * Payco 로그인을 위해 사용자를 인증해주기 위한 Service.
 *
 * @author koesnam (추만석)
 * @since 2023.07.30
 */

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
@Slf4j
@RequiredArgsConstructor
@Service
public class PaycoOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private static final ParameterizedTypeReference<PaycoAuthenticationResponseDto> PARAMETERIZED_RESPONSE_TYPE = new ParameterizedTypeReference<>() {};
    private final AuthApiAdapter authApiAdapter;
    private final RestOperations restOperations = new RestTemplate();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Assert.notNull(userRequest, "userRequest cannot be null");
        String clientId = userRequest.getClientRegistration().getClientId();
        String accessToken = userRequest.getAccessToken().getTokenValue();
        String userInfoRequestUrl = userRequest.getClientRegistration()
            .getProviderDetails()
            .getUserInfoEndpoint()
            .getUri();

        RequestEntity<Void> request = RequestEntity.post(userInfoRequestUrl)
            .header("client_id", clientId)
            .header("access_token", accessToken)
            .build();

        ResponseEntity<PaycoAuthenticationResponseDto> response = restOperations.exchange(request,
            PARAMETERIZED_RESPONSE_TYPE);
        Map<String, Object> attributes = Objects.requireNonNull(response.getBody())
            .getAttributes();

        String idNo = (String) attributes.get("idNo");
        String provider = userRequest.getClientRegistration().getClientName();

        AuthenticationResponseDto responseDto;
        try {
            responseDto = authApiAdapter.sendOAuthUserId(provider, idNo).getBody();
        } catch (HttpClientErrorException e) {
            if (e.getRawStatusCode() == 404) {
                attributes.put("provider", provider);
                attributes.put("accountCode", idNo);
                throw new OAuth2AccountNotFoundException(attributes);
            }
            log.error("OAuth2 로그인 과정 중 인증서버에서 실패 \n\n상태코드 : {}, 에러메세지 : {}, OAuth2 공급자명 : {} , 회원식별자 : {}",
                e.getRawStatusCode(), e.getMessage(), provider, idNo);
            throw e;
        } catch (HttpServerErrorException e2) {
            log.error("OAuth2 로그인 중 외부 서버 내부 오류 발생");
            throw new AuthenticationServiceException("서버와 통신 실패");
        } catch (Exception e3) {
            log.error("OAuth2 로그인 과정 중 프론트서버에서 실패 \n\n발생한 예외 : {} , 에러메세지 : {} \n\n --> {}", e3.getClass(),
                e3.getMessage(), ExceptionUtils.getStackTrace(e3));
            throw new AuthenticationServiceException("서버로 송신 실패");
        }
        Assert.notNull(responseDto, "기존 회원이면 인증된 토큰들이 담겨있습니다.");
        return CommonAccount.authenticated(responseDto.getAccessToken(), responseDto.getRefreshToken());
    }
}
