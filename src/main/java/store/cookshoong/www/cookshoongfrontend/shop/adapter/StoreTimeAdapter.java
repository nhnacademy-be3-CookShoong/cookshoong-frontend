package store.cookshoong.www.cookshoongfrontend.shop.adapter;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.common.property.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateBusinessHourRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateHolidayRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectBusinessHourResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectHolidayResponseDto;

/**
 * 매장 영업시간, 휴업일의 Adapter
 *
 * @author papel (윤동현)
 * @since 2023.07.14
 */
@Component
@RequiredArgsConstructor
public class StoreTimeAdapter {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    /**
     * 영업시간을 등록하는 메서드.
     *
     * @param storeId                      매장아이디
     * @param createBusinessHourRequestDto 사업자가 영업시간을 등록하는 Dto
     */
    public ResponseEntity<Void> executeCreateBusinessHour(Long storeId,
                                          CreateBusinessHourRequestDto createBusinessHourRequestDto) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("business-hour")
            .buildAndExpand(storeId)
            .toUri();

        RequestEntity<CreateBusinessHourRequestDto> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(createBusinessHourRequestDto);
        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });
    }

    /**
     * 휴업일를 등록하는 메서드.
     *
     * @param storeId                 매장아이디
     * @param createHolidayRequestDto 사업자가 휴업일을 등록하는 Dto
     */
    public ResponseEntity<Void> executeCreatHoliday(Long storeId,
                                    CreateHolidayRequestDto createHolidayRequestDto) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("holiday")
            .buildAndExpand(storeId)
            .toUri();

        RequestEntity<CreateHolidayRequestDto> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(createHolidayRequestDto);
        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });
    }

    /**
     * 영업시간 리스트 조회 메서드.
     *
     * @param storeId 매장 아이디
     * @return response
     */
    public List<SelectBusinessHourResponseDto> fetchBusinessHours(Long storeId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("business-hour")
            .buildAndExpand(storeId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        ResponseEntity<List<SelectBusinessHourResponseDto>> response
            = restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });

        return response.getBody();
    }

    /**
     * 휴업일 리스트 조회 메서드.
     *
     * @param storeId 매장 아이디
     * @return response
     */
    public List<SelectHolidayResponseDto> fetchHolidays(Long storeId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("holiday")
            .buildAndExpand(storeId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        ResponseEntity<List<SelectHolidayResponseDto>> response
            = restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });

        return response.getBody();
    }

    /**
     * 영업시간 삭제 메서드.
     *
     * @param storeId        매장 아이디
     * @param businessHourId 영업시간 아이디
     */
    public ResponseEntity<Void> executeDeleteBusinessHour(Long storeId, Long businessHourId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("business-hour")
            .pathSegment("{business-hour}")
            .buildAndExpand(storeId, businessHourId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.delete(uri).build();
        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });
    }

    /**
     * 휴업일 삭제 메서드.
     *
     * @param storeId    매장 아이디
     * @param holidayId  휴업일 아이디
     */
    public ResponseEntity<Void> executeDeleteHoliday(Long storeId, Long holidayId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("holiday")
            .pathSegment("{holiday}")
            .buildAndExpand(storeId, holidayId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.delete(uri).build();
        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });
    }
}
