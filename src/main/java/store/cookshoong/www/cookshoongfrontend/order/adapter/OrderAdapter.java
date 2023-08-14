package store.cookshoong.www.cookshoongfrontend.order.adapter;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.common.property.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.common.util.RestResponsePage;
import store.cookshoong.www.cookshoongfrontend.order.model.request.CreateOrderRequestDto;
import store.cookshoong.www.cookshoongfrontend.order.model.request.PatchOrderRequestDto;
import store.cookshoong.www.cookshoongfrontend.order.model.response.CreateOrderResponseDto;
import store.cookshoong.www.cookshoongfrontend.order.model.response.SelectAccountOrderInStatusResponseDto;
import store.cookshoong.www.cookshoongfrontend.order.model.response.SelectOrderInStatusResponseDto;
import store.cookshoong.www.cookshoongfrontend.order.model.response.SelectOrderPossibleResponseDto;

/**
 * 주문 어뎁터.
 *
 * @author eora21 (김주호)
 * @since 2023.08.06
 */
@Component
@RequiredArgsConstructor
public class OrderAdapter {
    private static final String ORDER = "/api/orders";
    private static final String STORE_ID = "{storeId}";
    private static final String STATUS = "status";

    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    /**
     * 주문 생성.
     *
     * @param createOrderRequestDto the create order request dto
     * @return the page
     */
    public CreateOrderResponseDto executeOrder(CreateOrderRequestDto createOrderRequestDto) {
        ResponseEntity<CreateOrderResponseDto> response = restTemplate.exchange(
                UriComponentsBuilder
                        .fromUriString(apiProperties.getGatewayUrl())
                        .path("/api/orders")
                        .build()
                        .toUri(),
                HttpMethod.POST,
                new HttpEntity<>(createOrderRequestDto),
                new ParameterizedTypeReference<>() {
                });

        return response.getBody();
    }

    /**
     * 주문 가능한 지 여부를 파악.
     *
     * @param storeId   the store id
     * @param accountId the account id
     * @return the select order possible response dto
     */
    public SelectOrderPossibleResponseDto fetchOrderPossible(Long storeId, Long accountId) {
        ResponseEntity<SelectOrderPossibleResponseDto> response = restTemplate.exchange(
                UriComponentsBuilder
                        .fromUriString(apiProperties.getGatewayUrl())
                        .path("/api/orders/{storeId}/possible/{accountId}")
                        .buildAndExpand(storeId, accountId)
                        .toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        return response.getBody();
    }

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

    /**
     * Fetch own order page.
     *
     * @param accountId the account id
     * @param pageable  the pageable
     * @return the page
     */
    public Page<SelectAccountOrderInStatusResponseDto> fetchOwnOrder(Long accountId, Pageable pageable) {
        ResponseEntity<RestResponsePage<SelectAccountOrderInStatusResponseDto>> response = restTemplate.exchange(
                RestResponsePage.pageableToParameter(UriComponentsBuilder
                        .fromUriString(apiProperties.getGatewayUrl())
                        .path("/api/my-page/{accountId}/orders")
                        .buildAndExpand(accountId), pageable),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        return response.getBody();
    }
}
