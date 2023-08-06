package store.cookshoong.www.cookshoongfrontend.payment.adapter.paymentbackapi;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.common.property.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.CreatePaymentDto;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.CreateRefundDto;
import store.cookshoong.www.cookshoongfrontend.payment.model.response.TossPaymentKeyResponseDto;

/**
 * 결제 Back API 에 대한 Adapter.
 *
 * @author jeongjewan
 * @since 2023.08.03
 */
@Component
@RequiredArgsConstructor
public class PaymentAdapter {

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

        restTemplate.exchange(uri, POST, httpEntity, new ParameterizedTypeReference<>() {});
    }

    /**
     * 결제 취소(환불) & 부분 후 성공한 데이터를 Back API 보내는 메서드.
     *
     * @param createRefundDto       성공한 취소(환불)에 대한 데이터
     */
    public void executeRefund(CreateRefundDto createRefundDto) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("payments")
            .pathSegment("refunds")
            .build().toUri();

        HttpEntity<CreateRefundDto> httpEntity = new HttpEntity<>(createRefundDto);

        restTemplate.exchange(uri, POST, httpEntity, new ParameterizedTypeReference<>() {});
    }

    /**
     * 토스 API 결제 조회 및 취소에 필요한 paymentKey 응답받는 Adapter.
     *
     * @param orderCode     주문코드
     * @return              paymentKey
     */
    public TossPaymentKeyResponseDto fetchTossPaymentKey(UUID orderCode) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("payments")
            .pathSegment("charges")
            .pathSegment("{orderCode}")
            .pathSegment("select-payment")
            .buildAndExpand(orderCode)
            .toUri();

        ResponseEntity<TossPaymentKeyResponseDto> exchange =
            restTemplate.exchange(uri, GET, null, new ParameterizedTypeReference<>() {});

        return exchange.getBody();
    }

    /**
     * 환불금액이 결제금액을 넘어가는지 검증하는 Adapter.
     *
     * @param chargeCode        결제 코드
     * @param refundAmount      환불 금액
     * @return                  환불 금액이 결제금액을 넘어가면 true, 그렇지 않으면 false
     */
    public Boolean fetchIsRefundAmountExceedsChargedAmount(UUID chargeCode, Integer refundAmount) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("payments")
            .pathSegment("charges")
            .pathSegment("{chargeCode}")
            .pathSegment("refunds")
            .pathSegment("verify")
            .queryParam("refundAmount", refundAmount)
            .buildAndExpand(chargeCode)
            .toUri();

        ResponseEntity<Boolean> exchange =
            restTemplate.exchange(uri, GET, null, new ParameterizedTypeReference<>() {});

        return exchange.getBody();
    }
}
