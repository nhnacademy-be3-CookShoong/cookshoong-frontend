package store.cookshoong.www.cookshoongfrontend.auth.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.Map;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import store.cookshoong.www.cookshoongfrontend.auth.resolver.CustomOAuth2AuthorizationRequestResolver;
import store.cookshoong.www.cookshoongfrontend.common.service.SKMService;

/**
 * OAuth2 로그인 관련 설정 빈들을 모아놓은 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.08.06
 */
@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MethodName"})
@Configuration
public class OAuth2Config {

    /**
     * 별도로 관리하는 OAuth2 설정정보를 가지는 빈을 등록시키는 메서드.
     *
     * @param baseUri     the base uri
     * @param oauth2Keyid the oauth 2 keyid
     * @param skmService  the skm service
     * @return the o auth 2 properties
     * @throws JsonProcessingException the json processing exception
     */
    @Bean
    @SuppressWarnings({"unchecked", "checkstyle:LocalVariableName"})
    public OAuth2Properties oAuth2Properties(@Value("${cookshoong.oauth2.base-uri}") String baseUri,
                                             @Value("${cookshoong.skm.keyid.oauth2}") String oauth2Keyid,
                                             SKMService skmService) throws JsonProcessingException {
        OAuth2Properties oAuth2Properties = new OAuth2Properties();
        oAuth2Properties.setBaseUri(baseUri);
        oAuth2Properties.setClientSecrets(skmService.fetchSecrets(oauth2Keyid, Map.class));
        return oAuth2Properties;
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(List<ClientRegistration> registrations) {
        return new InMemoryClientRegistrationRepository(registrations);
    }

    /**
     * Payco 로그인을 적용하기위해 필요한 설정값들을 가지는 빈을 등록시키는 메서드.
     *
     * @param oAuth2Properties the o auth 2 properties
     * @return the client registration
     */
    @Bean
    public ClientRegistration paycoClient(OAuth2Properties oAuth2Properties) {
        return ClientRegistration.withRegistrationId("payco")
            .clientName("Payco")
            .clientId("3RDHlYSDk3YqKuIscCG_Jta")
            .clientSecret(oAuth2Properties.getClientSecretOf("payco"))
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
            .scope("idNo", "name", "mobile", "email", "birthdayMMdd")
            .redirectUri(oAuth2Properties.getBaseUri() + "/login/oauth2/code/payco")
            .authorizationUri("https://id.payco.com/oauth2.0/authorize")
            .tokenUri("https://id.payco.com/oauth2.0/token")
            .userInfoUri("https://apis-payco.krp.toastoven.net/payco/friends/find_member_v2.json")
            .userNameAttributeName("idNo")
            .build();
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }

    /**
     * Payco 로그인시 Payco 에서 요구하는 파라미터들을 추가해주기 위해 커스텀한 RequestResolver 빈을 등록하는 메서드.
     *
     * @param clientRegistrationRepository the client registration repository
     * @return the o auth 2 authorization request resolver
     */
    @Bean
    public OAuth2AuthorizationRequestResolver oAuth2AuthorizationRequestResolver(ClientRegistrationRepository clientRegistrationRepository) {
        return new CustomOAuth2AuthorizationRequestResolver(
            new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository,
                "/oauth2/authorization"));
    }

    @Setter
    private static class OAuth2Properties {
        private String baseUri;
        private Map<String, String> clientSecrets;

        public String getBaseUri() {
            return baseUri;
        }

        public String getClientSecretOf(String registrationId) {
            return clientSecrets.get(registrationId);
        }
    }
}
