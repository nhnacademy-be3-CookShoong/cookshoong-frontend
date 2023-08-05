package store.cookshoong.www.cookshoongfrontend.payment.adapter.paymentbackapi;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.common.property.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.CreateTypeRequestDto;
import store.cookshoong.www.cookshoongfrontend.payment.model.response.TypeResponseDto;

/**
 * 결제 타입에 대한 Adapter.
 *
 * @author jeongjewan
 * @since 2023.07.07
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ChargeTypeAdapter {

    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    /**
     * 결제 타입을 등록하는 Adapter 메서드.
     *
     * @param createTypeRequestDto  결제 타입에 이름을 생성하는 Dto
     */
    public void executeChargeType(CreateTypeRequestDto createTypeRequestDto) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("payments")
            .pathSegment("charges")
            .pathSegment("charge-type")
            .build().toUri();

        HttpEntity<CreateTypeRequestDto> httpEntity = new HttpEntity<>(createTypeRequestDto);

        restTemplate.exchange(uri, POST, httpEntity, new ParameterizedTypeReference<>() {});
    }

    /**
     * 해당 아이디에 대한 결제 타입을 조회하는 메서드.
     *
     * @param chargeTypeId        결제 타입 아이디
     * @return                    해당되는 결제 타입을 반환
     */
    public TypeResponseDto fetchChargeType(String chargeTypeId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("payments")
            .pathSegment("charges")
            .pathSegment("charge-type")
            .pathSegment("{chargeTypeId}")
            .buildAndExpand(chargeTypeId)
            .toUri();

        ResponseEntity<TypeResponseDto> exchange =
            restTemplate.exchange(uri, GET, null, new ParameterizedTypeReference<>() {});

        return exchange.getBody();
    }

    /**
     * 모든 결제 타입에 대한 조회 메서드.
     *
     * @return          모든 결제 타입 반환
     */
    public List<TypeResponseDto> fetchChargeTypeAll() {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("payments")
            .pathSegment("charges")
            .pathSegment("charge-type")
            .build().toUri();

        ResponseEntity<List<TypeResponseDto>> exchange =
            restTemplate.exchange(uri, GET, null, new ParameterizedTypeReference<>() {});

        return exchange.getBody();
    }

    /**
     * 해당 결제 타입을 삭제하는 메서드.
     *
     * @param id        결제 타입 아이디
     */
    public void eraseChargeType(String id) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("payments")
            .pathSegment("charges")
            .pathSegment("charge-type")
            .pathSegment("{id}")
            .buildAndExpand(id)
            .toUri();

        restTemplate.exchange(uri, DELETE, null, new ParameterizedTypeReference<>() {});
    }
}
