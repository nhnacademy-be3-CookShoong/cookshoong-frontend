package store.cookshoong.www.cookshoongfrontend.swagger.controller;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import store.cookshoong.www.cookshoongfrontend.swagger.service.SwaggerService;

/**
 * swagger-ui 컨트롤러.
 *
 * @author eora21 (김주호)
 * @since 2023.08.19
 */
@RestController
@RequiredArgsConstructor
public class SwaggerController {
    private final SwaggerService swaggerService;

    /**
     * swagger-ui 엔드포인트.
     *
     * @return the swagger ui
     * @throws IOException the io exception
     */
    @GetMapping("/swagger-ui")
    public ResponseEntity<Resource> getSwaggerUi() throws IOException {
        swaggerService.selectSwaggerUi();

        Resource resource = new ClassPathResource("static/swagger-ui/swagger-ui.html");
        return ResponseEntity.ok(resource);
    }
}
