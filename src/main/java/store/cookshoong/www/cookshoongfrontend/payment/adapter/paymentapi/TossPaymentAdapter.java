package store.cookshoong.www.cookshoongfrontend.payment.adapter.paymentapi;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.UUID;
import net.minidev.json.JSONObject;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.common.property.TossProperties;
import store.cookshoong.www.cookshoongfrontend.payment.model.response.TossResponseDto;

/**
 * 토스에서 인증된 결제를 카드사에 승인 or 쉬소에 대해서 통신하는 Adapter.
 *
 * @author jeongjewan
 * @since 2023.08.02
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TossPaymentAdapter {

    private final RestTemplate restTemplate = new RestTemplate();
    private final TossProperties tossProperties;

    /**
     * 토스 결제 승인을 요청하는 메서드.
     *
     * @return                              승인된 결제에 대한 응답을 반환
     */
    public TossResponseDto requestApproveTossPayment(String paymentKey, Long amount,
                                                     UUID orderId) {

        final String url = UriComponentsBuilder.newInstance()
            .scheme("https")
            .host("api.tosspayments.com")
            .path("/v1/payments/confirm")
            .build().toUriString();

        JSONObject param = new JSONObject();
        param.put("paymentKey", paymentKey);
        param.put("amount", amount);
        param.put("orderId", orderId);

        return restTemplate.exchange(url, POST, new HttpEntity<>(param,
            getTossSecretHeaders()), TossResponseDto.class).getBody();
    }

    /**
     * 토스 결제 취소를 요청하는 메서드.
     *
     * @param paymentKey        paymentKey
     * @param cancelReason      cancelReason
     * @return                  결제 취소에 대한 응답을 반환
     */
    public TossResponseDto requestCancelTossPayment(String paymentKey, String cancelReason) {

        final String url = UriComponentsBuilder.newInstance()
            .scheme("https")
            .host("api.tosspayments.com")
            .path(paymentKey)
            .path("/cancel")
            .build().toUriString();

        JSONObject param = new JSONObject();
        param.put("cancelReason", cancelReason);


        return restTemplate.exchange(url, POST, new HttpEntity<>(param,
            getTossSecretHeaders()), TossResponseDto.class).getBody();
    }

    /**
     * 토스 부분 결제 취소를 요청하는 메서드.
     *
     * @param paymentKey        paymentKey
     * @param cancelReason      cancelReason
     * @return                  결제 취소에 대한 응답을 반환
     */
    public TossResponseDto requestPartialCancelTossPayment(String paymentKey, String cancelReason, Integer cancelAmount) {

        final String url = UriComponentsBuilder.newInstance()
            .scheme("https")
            .host("api.tosspayments.com")
            .path(paymentKey)
            .path("/cancel")
            .build().toUriString();

        JSONObject param = new JSONObject();
        param.put("cancelReason", cancelReason);
        param.put("cancelAmount", cancelAmount);


        return restTemplate.exchange(url, POST, new HttpEntity<>(param,
            getTossSecretHeaders()), TossResponseDto.class).getBody();
    }

    /**
     * 토스 승인 요청을 할 때 필요한 헤더 생성.
     *
     * @return header.
     */
    private HttpHeaders getTossSecretHeaders() {

        HttpHeaders headers = new HttpHeaders();
        String encodeAuthKey =
            Base64.getEncoder().encodeToString((tossProperties.getTossSecret().getSecret().trim() + ":").getBytes());
        headers.setContentType(APPLICATION_JSON);
        headers.setBasicAuth(encodeAuthKey);

        return headers;
    }
}
