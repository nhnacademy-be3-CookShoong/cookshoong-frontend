package store.cookshoong.www.cookshoongfrontend.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import store.cookshoong.www.cookshoongfrontend.common.aop.ErrorLoggingAspect;

/**
 * Aop 관련 설정을 해주기위한 Config 빈.
 *
 * @author koesnam (추만석)
 * @since 2023.08.22
 */
@Configuration
@EnableAspectJAutoProxy
public class AopConfig {
    @Bean
    public ErrorLoggingAspect errorLoggingAspect() {
        return new ErrorLoggingAspect();
    }
}
