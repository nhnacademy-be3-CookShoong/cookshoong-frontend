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
import store.cookshoong.www.cookshoongfrontend.store.exception.CreateHolidayFailureException;
import store.cookshoong.www.cookshoongfrontend.store.model.CreateMenuRequestDto;

/**
 * 매장의 메뉴 관리를 위한 서비스.
 *
 * @author papel
 * @since 2023.07.09
 */
@Service
@RequiredArgsConstructor
public class StoreMenuManagerService {
    private final RestTemplate restTemplate;

    private final ApiProperties apiProperties;

    /**
     * API 서버에 메뉴 신규등록 요청을 보냄.
     *
     * @param createMenuRequestDto 매장 메뉴 신규등록 요청 정보
     */
    public void createMenu(CreateMenuRequestDto createMenuRequestDto) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getBaseUrl())
            .path("/api/stores/1/menu")
            .encode()
            .build()
            .toUri();

        RequestEntity<CreateMenuRequestDto> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(createMenuRequestDto);

        ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);

        if(!response.getStatusCode().is2xxSuccessful()) {
            throw new CreateHolidayFailureException(response.getStatusCode());
        }
    }
}
