package store.cookshoong.www.cookshoongfrontend.store.service;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.common.config.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.store.exception.CreateStoreFailureException;
import store.cookshoong.www.cookshoongfrontend.store.model.CreateStoreRequestDto;

/**
 * 매장의 등록 및 조회를 위한 컨트롤러.
 *
 * @author papel
 * @since 2023.07.09
 */
@Service
@RequiredArgsConstructor
public class StoreService {
    private final RestTemplate restTemplate;

    private final ApiProperties apiProperties;

    /**
     * API 서버에 매장 신규등록 요청을 보냄.
     *
     * @param createStoreRequestDto 매장 신규등록 요청 정보
     */
    public void createStore(CreateStoreRequestDto createStoreRequestDto) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .path("/api/accounts/1/stores")
            .encode()
            .build()
            .toUri();

        RequestEntity<CreateStoreRequestDto> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(createStoreRequestDto);

        ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);

        if(!response.getStatusCode().is2xxSuccessful()) {
            throw new CreateStoreFailureException(response.getStatusCode());
        }
    }

}
