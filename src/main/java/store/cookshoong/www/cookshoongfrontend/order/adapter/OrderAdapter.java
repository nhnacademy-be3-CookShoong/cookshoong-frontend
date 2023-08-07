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
}
