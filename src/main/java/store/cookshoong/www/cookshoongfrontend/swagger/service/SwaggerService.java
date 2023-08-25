package store.cookshoong.www.cookshoongfrontend.swagger.service;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.swagger.adapter.SwaggerAdapter;

/**
 * swagger 요청 서비스.
 *
 * @author eora21 (김주호)
 * @since 2023.08.22
 */
@Service
@RequiredArgsConstructor
public class SwaggerService {
    private final SwaggerAdapter swaggerAdapter;

    /**
     * openapi json 응답.
     *
     */
    public Map<String, Object> selectSwaggerUi() {
        return swaggerAdapter.fetchSwaggerUi();
    }
}
