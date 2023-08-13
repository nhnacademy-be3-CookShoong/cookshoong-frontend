package store.cookshoong.www.cookshoongfrontend.auth.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import store.cookshoong.www.cookshoongfrontend.common.service.SKMService;

/**
 * 리프레쉬 토큰를 암호화하는 솔트와 패스워드를 등록하는 설정 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.08.12
 */
@Configuration
public class TokenKeyConfig {
    @Bean
    public TokenKeyProperties tokenKeyProperties(@Value("${cookshoong.skm.keyid.token-key}") String tokenKeyid,
                                                 SKMService skmService)
        throws JsonProcessingException {
        return skmService.fetchSecrets(tokenKeyid, TokenKeyProperties.class);
    }

    @Bean
    public TextEncryptor textEncryptor(TokenKeyProperties tokenKeyProperties) {
        return Encryptors.text(tokenKeyProperties.getPassword(), tokenKeyProperties.getSalt());
    }

    @Getter
    private static class TokenKeyProperties {
        private String salt;
        private String password;
    }
}
