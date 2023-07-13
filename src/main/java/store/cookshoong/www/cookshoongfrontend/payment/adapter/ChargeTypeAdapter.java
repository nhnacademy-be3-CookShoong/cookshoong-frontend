package store.cookshoong.www.cookshoongfrontend.payment.adapter;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import store.cookshoong.www.cookshoongfrontend.common.config.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.CreateTypeRequestDto;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.ModifyTypeRequestDto;
import store.cookshoong.www.cookshoongfrontend.payment.model.response.TypeResponseDto;

/**
 * 환불 타입과 결제 타입에 대한 Adapter.
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
    public void createChargeType(CreateTypeRequestDto createTypeRequestDto) {

        HttpEntity<CreateTypeRequestDto> httpEntity = new HttpEntity<>(createTypeRequestDto);

        restTemplate.exchange(apiProperties.getGatewayUrl() + "/api/payments/charges", POST, httpEntity,
            new ParameterizedTypeReference<>() {
            });
    }

    /**
     * 해당 아이디에 대한 결제 타입을 조회하는 메서드.
     *
     * @param chargeTypeId        결제 타입 아이디
     * @return          해당되는 결제 타입을 반환
     */
    public TypeResponseDto selectChargeType(String chargeTypeId) {

        ResponseEntity<TypeResponseDto> exchange =
            restTemplate.exchange(apiProperties.getGatewayUrl() + "/api/payments/charges" + "/" + chargeTypeId,
                GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        return exchange.getBody();
    }

    /**
     * 모든 결제 타입에 대한 조회 메서드.
     *
     * @return          모든 결제 타입 반환
     */
    public List<TypeResponseDto> selectChargeTypeAll() {

        log.info("<><><>>> : {}", apiProperties.getGatewayUrl());

        ResponseEntity<List<TypeResponseDto>> exchange =
            restTemplate.exchange(apiProperties.getGatewayUrl() + "/api/payments/charges",
                GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        return exchange.getBody();
    }

    /**
<<<<<<< HEAD
     * 해당 결제 타입에 대한 이름을 수정하는 메서드.
     *
     * @param id                    결제 타입 아이디
     * @param modifyTypeRequestDto  결제 타입에 이름을 수정하는 Dto
     */
    public void modifyChargeType(Long id, ModifyTypeRequestDto modifyTypeRequestDto) {

        HttpEntity<ModifyTypeRequestDto> httpEntity = new HttpEntity<>(modifyTypeRequestDto);

        restTemplate.exchange(apiProperties.getGatewayUrl() + "/api/payments/charges" + "/" + id,
            PUT,
            httpEntity,
            new ParameterizedTypeReference<>() {
            });
    }

    /**
=======
>>>>>>> a03d8f7 (refactor: 💡 수정 DB 수정으로 인한 결제 및 환불 타입에 Front 변경)
     * 해당 결제 타입을 삭제하는 메서드.
     *
     * @param id        결제 타입 아이디
     */
    public void deleteChargeType(String id) {

        restTemplate.exchange(apiProperties.getGatewayUrl() + "/api/payments/charges" + "/" + id,
            DELETE,
            null,
            new ParameterizedTypeReference<>() {
            });
    }
}
