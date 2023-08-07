package store.cookshoong.www.cookshoongfrontend.auth.resolver;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

/**
 * Payco 로그인시 Payco 에서 요구하는 요청 파라미터를 추가해주기 위해 생성한 클래스.
 * resolve(HttpServletRequest request) -> OAuth2 로그인시 사용되는 메서드
 * resolve(HttpServletRequest request, String clientRegistrationId) -> OAuth2 인가시 사용되는 메서드
 *
 * @author koesnam (추만석)
 * @since 2023.08.06
 */
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
@RequiredArgsConstructor
public class CustomOAuth2AuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {
    private final DefaultOAuth2AuthorizationRequestResolver defaultOAuth2AuthorizationRequestResolver;

    private static boolean isPaycoLogin(HttpServletRequest request) {
        return request.getRequestURI().contains("payco");
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        if (isPaycoLogin(request)) {
            defaultOAuth2AuthorizationRequestResolver.setAuthorizationRequestCustomizer(
                customizer -> customizer.additionalParameters(
                    Map.of("serviceProviderCode", "FRIENDS", "userLocale", "ko_KR")
                )
            );
        } else {
            defaultOAuth2AuthorizationRequestResolver.setAuthorizationRequestCustomizer(
                customizer -> {}
            );
        }
        return defaultOAuth2AuthorizationRequestResolver.resolve(request);
    }


    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        return defaultOAuth2AuthorizationRequestResolver.resolve(request, clientRegistrationId);
    }
}
