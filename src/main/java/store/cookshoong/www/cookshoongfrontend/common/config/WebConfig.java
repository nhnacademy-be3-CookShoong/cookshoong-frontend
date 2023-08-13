package store.cookshoong.www.cookshoongfrontend.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import store.cookshoong.www.cookshoongfrontend.auth.interceptor.TokenReissueInterceptor;
import store.cookshoong.www.cookshoongfrontend.auth.service.TokenManagementService;

/**
 * WebMvc 관련 설정을 하기위한 Configuration 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.27
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final TokenManagementService tokenManagementService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenReissueInterceptor(tokenManagementService));
    }
}
