package store.cookshoong.www.cookshoongfrontend.common.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * api 관련 설정값들을 다루기 위한 클래스.
 *
 * @author koesnam
 * @since 2023.07.08
 */
@Getter
@Setter
@Component
@ConfigurationProperties("cookshoong")
public class ApiProperties {
    String gatewayUrl;
}
