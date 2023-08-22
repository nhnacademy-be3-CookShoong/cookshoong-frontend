package store.cookshoong.www.cookshoongfrontend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * swagger의 classpath 접근을 위한 WebMvcConfigurer.
 *
 * @author eora21
 * @since 2023.08.22
 */
@Configuration
public class SwaggerRoutingConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger/**").addResourceLocations("classpath:/static/swagger-ui/");
    }
}
