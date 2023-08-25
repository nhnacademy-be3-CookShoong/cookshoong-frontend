package store.cookshoong.www.cookshoongfrontend.swagger.controller;

import java.util.Map;
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
     */
    @GetMapping("/swagger-ui")
    public ResponseEntity<Resource> getSwaggerUi() {
        swaggerService.selectSwaggerUi();

        Resource resource = new ClassPathResource("static/swagger-ui/swagger-ui.html");
        return ResponseEntity.ok(resource);
    }

    /**
     * swagger-ui json 엔드포인트.
     *
     * @return the swagger ui json
     */
    @GetMapping("/swagger-ui/json")
    public ResponseEntity<Map<String, Object>> getSwaggerUiJson() {
        return ResponseEntity.ok(swaggerService.selectSwaggerUi());
    }
}
