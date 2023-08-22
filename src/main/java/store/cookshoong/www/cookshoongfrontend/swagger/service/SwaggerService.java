package store.cookshoong.www.cookshoongfrontend.swagger.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
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
    private static final String CLASS_PATH_DIR = "static/swagger-ui";
    private static final String JSON_FILE_NAME = "/openapi-3.0.json";

    private final SwaggerAdapter swaggerAdapter;
    private final ObjectMapper objectMapper;

    /**
     * openapi json을 받아와 파일로 저장.
     *
     * @throws IOException the io exception
     */
    public void selectSwaggerUi() throws IOException {
        Map<String, Object> swaggerUiResponse = swaggerAdapter.fetchSwaggerUi();
        saveSwaggerUi(swaggerUiResponse);
    }

    private void saveSwaggerUi(Map<String, Object> response) throws IOException {
        ClassPathResource resource = new ClassPathResource(CLASS_PATH_DIR);
        Path path = Paths.get(resource.getURI());

        String jsonAsString = objectMapper.writeValueAsString(response);

        try (BufferedWriter bufferedWriter =
                 new BufferedWriter(new FileWriter(path + JSON_FILE_NAME, false))) {
            bufferedWriter.write(jsonAsString);
        }
    }
}
