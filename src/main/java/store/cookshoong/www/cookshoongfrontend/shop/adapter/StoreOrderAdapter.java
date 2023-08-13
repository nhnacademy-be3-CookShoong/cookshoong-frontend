package store.cookshoong.www.cookshoongfrontend.shop.adapter;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.common.property.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.common.util.RestResponsePage;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.PatchOrderRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectOrderInStatusResponseDto;

/**
 * 주문 어뎁터.
 *
 * @author eora21 (김주호)
 * @since 2023.08.10
 */
@Component
@RequiredArgsConstructor
public class StoreOrderAdapter {
    private static final String ORDER = "/api/orders";
    private static final String STORE_ID = "{storeId}";
    private static final String STATUS = "status";
    private static final String COMPLETE = "complete";

    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;


    /**
     * 진행중인 주문 확인.
     *
     * @param storeId the store id
     * @return the list
     */
    public List<SelectOrderInStatusResponseDto> fetchOrderInProcess(Long storeId) {
        ResponseEntity<List<SelectOrderInStatusResponseDto>> response = restTemplate.exchange(
            UriComponentsBuilder
                .fromUriString(apiProperties.getGatewayUrl())
                .path(ORDER)
                .pathSegment(STORE_ID)
                .buildAndExpand(storeId)
                .toUri(),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<>() {
            });

        return response.getBody();
    }

    /**
     * 주문 완료된 건에 대한 목록.
     *
     * @param storeId the store id
     * @return the list
     */
    public RestResponsePage<SelectOrderInStatusResponseDto> fetchOrderInComplete(Long storeId, Pageable pageable) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment(ORDER)
            .pathSegment(STORE_ID)
            .pathSegment(COMPLETE)
            .queryParam("page", pageable.getPageNumber())
            .queryParam("size", pageable.getPageSize())
            .buildAndExpand(storeId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .accept(APPLICATION_JSON)
            .build();

        ResponseEntity<RestResponsePage<SelectOrderInStatusResponseDto>> response =
            restTemplate.exchange(request, new ParameterizedTypeReference<>() {
            });

        return response.getBody();
    }

    /**
     * 주문 상태 변경.
     *
     * @param patchOrderRequestDto the patch order request dto
     */
    public void changeStoreOrderStatus(PatchOrderRequestDto patchOrderRequestDto) {
        restTemplate.exchange(
            UriComponentsBuilder
                .fromUriString(apiProperties.getGatewayUrl())
                .path(ORDER)
                .pathSegment(STATUS)
                .build()
                .toUri(),
            HttpMethod.PATCH,
            new HttpEntity<>(patchOrderRequestDto),
            new ParameterizedTypeReference<>() {
            });
    }
}
