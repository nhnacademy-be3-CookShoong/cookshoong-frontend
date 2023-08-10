package store.cookshoong.www.cookshoongfrontend;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
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
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static void main(String[] args) {
        SpringApplication.run(CookshoongFrontendApplication.class, args);
    }
}
