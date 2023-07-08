package store.cookshoong.www.cookshoongfrontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * cookshoong-frontend entrypoint.
 *
 * @author koesnam, ...
 * @since 2023/07/04
 */
@ConfigurationPropertiesScan
@SpringBootApplication
public class CookshoongFrontendApplication {
    public static void main(String[] args) {
        SpringApplication.run(CookshoongFrontendApplication.class, args);
    }
}
