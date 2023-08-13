package store.cookshoong.www.cookshoongfrontend.order.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.common.property.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.order.model.request.CreateOrderRequestDto;
import store.cookshoong.www.cookshoongfrontend.order.model.response.CreateOrderResponseDto;
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
}
