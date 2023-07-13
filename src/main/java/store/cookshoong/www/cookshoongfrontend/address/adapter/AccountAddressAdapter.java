package store.cookshoong.www.cookshoongfrontend.address.adapter;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import store.cookshoong.www.cookshoongfrontend.address.model.request.CreateAccountAddressRequestDto;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AccountAddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.common.config.ApiProperties;

/**
 * 회원과 주소 간에 Adapter.
 *
 * @author jeongjewan
 * @since 2023.07.09
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AccountAddressAdapter {

    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    /**
     * 회원이 주소를 등록하는 메서드.
     *
     * @param accountId                         회원 기본키
     * @param createAccountAddressRequestDto    회원이 주소를 등록하는 Dto
     */
    public void createAccountAddress(Long accountId, CreateAccountAddressRequestDto createAccountAddressRequestDto) {

        HttpEntity<CreateAccountAddressRequestDto> httpEntity =
            new HttpEntity<>(createAccountAddressRequestDto);

        restTemplate.exchange(apiProperties.getGatewayUrl() + "/api/addresses" + "/" + accountId,
            POST,
            httpEntity,
            new ParameterizedTypeReference<>() {
            });
    }

    /**
     * 회원이 가지고 있는 모든 주소 조회.
     *
     * @param accountId         회원 기본키
     * @return                  회원이 등록한 모든 주소 반환
     */
    public List<AccountAddressResponseDto> selectAccountAddressAll(Long accountId) {

        ResponseEntity<List<AccountAddressResponseDto>> exchange =
            restTemplate.exchange(apiProperties.getGatewayUrl() + "/api/addresses" + "/" + accountId,
                GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        return exchange.getBody();
    }

    /**
     * 회원이 지정한 주소 삭제.
     *
     * @param accountId         회원 기본키
     * @param addressId         주소 아이디
     */
    public void deleteAccountAddress(Long accountId, Long addressId) {

        restTemplate.exchange(apiProperties.getGatewayUrl() + "/api/addresses" + "/" + accountId + "/" + addressId,
            DELETE,
            null,
            new ParameterizedTypeReference<>() {
            });
    }
}
