package store.cookshoong.www.cookshoongfrontend.swagger.adapter;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.common.property.ApiProperties;

/**
 * swagger 요청 어뎁터.
 *
 * @author eora21 (김주호)
 * @since 2023.08.22
 */
@Component
@RequiredArgsConstructor
public class SwaggerAdapter {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    /**
     * Fetch swagger ui.
     */
    public Map<String, Object> fetchSwaggerUi() {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
            UriComponentsBuilder
                .fromUriString(apiProperties.getGatewayUrl())
                .path("/api/swagger-ui")
                .build()
                .toUri(),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<>() {
            });

        return response.getBody();
    }
}
