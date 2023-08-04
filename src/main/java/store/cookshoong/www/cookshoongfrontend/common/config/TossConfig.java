package store.cookshoong.www.cookshoongfrontend.common.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import store.cookshoong.www.cookshoongfrontend.common.property.TossProperties;
import store.cookshoong.www.cookshoongfrontend.common.property.TossSecret;
import store.cookshoong.www.cookshoongfrontend.common.property.TossUrl;
import store.cookshoong.www.cookshoongfrontend.common.service.SKMService;

/**
 * Toss SKM 설정으로 인한 Bean 주입 Config.
 *
 * @author jeongjewan
 * @since 2023.08.01
 */
@Configuration
public class TossConfig {

    /**
     * SKM 로 부터 클라이언트 인증서를 보내 Toss 설정값들을 가져온다.
     *
     * @param tossKeyid     SKM 저장되있는 기밀 데이터의 아이디
     * @param successUrl    toss 결제 성공 url
     * @param failUrl       toss 결제 실패 url
     * @param skmService    SKM Service
     * @return              toss 설정 값
     */
    @Bean
    @Profile("!default")
    public TossProperties tossProperties(@Value("${cookshoong.skm.keyid.toss}") String tossKeyid,
                          @Value("${toss.successUrl}") String successUrl,
                          @Value("${toss.failUrl}") String failUrl,
                          SKMService skmService) throws JsonProcessingException {

        TossSecret jwtSecret = skmService.fetchSecrets(tossKeyid, TossSecret.class);
        TossUrl jwtTtl = new TossUrl(successUrl, failUrl);

        return new TossProperties(jwtSecret, jwtTtl);
    }
}
