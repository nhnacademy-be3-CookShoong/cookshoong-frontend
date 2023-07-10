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
import store.cookshoong.www.cookshoongfrontend.store.exception.CreateBusinessHourFailureException;
import store.cookshoong.www.cookshoongfrontend.store.exception.CreateHolidayFailureException;
import store.cookshoong.www.cookshoongfrontend.store.model.CreateBusinessHourRequestDto;
import store.cookshoong.www.cookshoongfrontend.store.model.CreateHolidayRequestDto;

/**
 * 매장의 영업시간 관리를 위한 서비스.
 *
 * @author papel
 * @since 2023.07.09
 */
@Service
@RequiredArgsConstructor
public class StoreTimeManagerService {
    private final RestTemplate restTemplate;

    private final ApiProperties apiProperties;

    /**
     * API 서버에 휴업일 신규등록 요청을 보냄.
     *
     * @param createStoreRequestDto 매장 신규등록 요청 정보
     */
    public void createHoliday(CreateHolidayRequestDto createStoreRequestDto) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getBaseUrl())
            .path("/api/stores/1/holiday")
            .encode()
            .build()
            .toUri();

        RequestEntity<CreateHolidayRequestDto> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(createStoreRequestDto);

        ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);

        if(!response.getStatusCode().is2xxSuccessful()) {
            throw new CreateHolidayFailureException(response.getStatusCode());
        }
    }


    /**
     * API 서버에 영업시간 신규등록 요청을 보냄.
     *
     * @param createBusinessHourRequestDto 매장 신규등록 요청 정보
     */
    public void createBusinessHour(CreateBusinessHourRequestDto createBusinessHourRequestDto) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getBaseUrl())
            .path("/api/stores/1/businesshour")
            .encode()
            .build()
            .toUri();

        RequestEntity<CreateBusinessHourRequestDto> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(createBusinessHourRequestDto);

        ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);

        if(!response.getStatusCode().is2xxSuccessful()) {
            throw new CreateBusinessHourFailureException(response.getStatusCode());
        }
    }
}
