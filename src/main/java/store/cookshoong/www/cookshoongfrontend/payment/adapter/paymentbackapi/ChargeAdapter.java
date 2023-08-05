package store.cookshoong.www.cookshoongfrontend.payment.adapter.paymentbackapi;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.common.property.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.CreatePaymentDto;

/**
 * 결제 Back API 에 대한 Adapter.
 *
 * @author jeongjewan
 * @since 2023.08.03
 */
@Component
@RequiredArgsConstructor
public class ChargeAdapter {

    private final ApiProperties apiProperties;
    private final RestTemplate restTemplate;

    /**
     * 결제 승인 후 결제 성공한 데이터를 Back API 에 보내는 메서드.
     *
     * @param createPaymentDto      성공한 결제에 대한 데이터
     */
    public void executePayment(CreatePaymentDto createPaymentDto) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("payments")
            .pathSegment("charges")
            .build().toUri();

        HttpEntity<CreatePaymentDto> httpEntity = new HttpEntity<>(createPaymentDto);

        restTemplate.exchange(uri, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {});
    }
}
