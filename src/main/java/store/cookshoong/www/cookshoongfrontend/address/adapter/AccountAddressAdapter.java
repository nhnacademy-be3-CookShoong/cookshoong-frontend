package store.cookshoong.www.cookshoongfrontend.address.adapter;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.address.model.request.CreateAccountAddressRequestDto;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AccountAddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.model.response.SelectAllStoresNotOutedResponseDto;
import store.cookshoong.www.cookshoongfrontend.common.property.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.util.RestResponsePage;

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
     * @param accountId                      회원 기본키
     * @param createAccountAddressRequestDto 회원이 주소를 등록하는 Dto
     */
    public void executeAccountAddress(Long accountId, CreateAccountAddressRequestDto createAccountAddressRequestDto) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("addresses")
            .pathSegment("{accountId}")
            .buildAndExpand(accountId)
            .toUri();

        HttpEntity<CreateAccountAddressRequestDto> httpEntity =
            new HttpEntity<>(createAccountAddressRequestDto);

        restTemplate.exchange(uri, POST, httpEntity, new ParameterizedTypeReference<>() {});
    }

    /**
     * 회원이 선택한 주소에 대한 갱신 날짜를 업데이트.
     * Backend Api 에서 날짜만 업데이트 해주기 때문에 요청 Entity 가 널 입니다.
     *
     * @param accountId      회원 기본키
     * @param addressId     주소 아이디
     */
    public void changeSelectAccountAddressRenewalAt(Long accountId, Long addressId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("addresses")
            .pathSegment("{accountId}")
            .pathSegment("select")
            .pathSegment("{addressId}")
            .buildAndExpand(accountId, addressId)
            .toUri();

        restTemplate.exchange(uri, PATCH, null, new ParameterizedTypeReference<>() {});
    }

    /**
     * 회원이 가지고 있는 모든 주소 조회.
     *
     * @param accountId 회원 기본키
     * @return 회원이 등록한 모든 주소 반환
     */
    public List<AccountAddressResponseDto> fetchAccountAddressAll(Long accountId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("addresses")
            .pathSegment("{accountId}")
            .buildAndExpand(accountId)
            .toUri();

        ResponseEntity<List<AccountAddressResponseDto>> exchange =
            restTemplate.exchange(uri, GET, null, new ParameterizedTypeReference<>() {});

        return exchange.getBody();
    }

    /**
     * 회원이 주소록 중 선택한 주소 조회.
     *
     * @param addressId         주소 아이디
     * @return                  회원이 가지고 있는 주소에 대한 모든 정보(메인 주소, 상세 주소, 위도, 경도) 반환
     */
    public AddressResponseDto fetchAccountChoiceAddress(Long addressId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("addresses")
            .pathSegment("{addressId}")
            .pathSegment("choice")
            .buildAndExpand(addressId)
            .toUri();

        ResponseEntity<AddressResponseDto> exchange =
            restTemplate.exchange(uri, GET, null, new ParameterizedTypeReference<>() {});

        return exchange.getBody();
    }

    /**
     * 회원이 최근에 등록한 주소 정보.
     *
     * @param accountId         회원 기본키
     * @return                  회원이 가지고 있는 주소에 대한 모든 정보(메인 주소, 상세 주소, 위도, 경도) 반환
     */
    public AddressResponseDto fetchAccountAddressRenewalAt(Long accountId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("addresses")
            .pathSegment("{accountId}")
            .pathSegment("renewal-at")
            .buildAndExpand(accountId)
            .toUri();

        ResponseEntity<AddressResponseDto> exchange =
            restTemplate.exchange(uri, GET, null, new ParameterizedTypeReference<>() {});

        return exchange.getBody();
    }

    /**
     * 회원의 현재 위치에서 3km 이내 위치한 모든 매장.
     *
     * @param addressId             주소 아이디
     * @param storeCategoryCode     매장 카테고리 코드
     * @param pageable              페이지 처리
     * @return                      매장 정보 반환
     */
    public RestResponsePage<SelectAllStoresNotOutedResponseDto> fetchStoresNotOuted(Long addressId,
                                                                                    String storeCategoryCode,
                                                                                    Pageable pageable) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("accounts")
            .pathSegment("customer")
            .pathSegment("{addressId}")
            .pathSegment("stores")
            .queryParam("storeCategoryCode", storeCategoryCode)
            .buildAndExpand(addressId)
            .toUri();

        ResponseEntity<RestResponsePage<SelectAllStoresNotOutedResponseDto>>  exchange = restTemplate.exchange(
            RestResponsePage.pageableToParameter(String.valueOf(uri), pageable),
            GET, null, new ParameterizedTypeReference<>() {});

        return exchange.getBody();
    }

    /**
     * 회원이 지정한 주소 삭제.
     *
     * @param accountId 회원 기본키
     * @param addressId 주소 아이디
     */
    public void eraseAccountAddress(Long accountId, Long addressId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("addresses")
            .pathSegment("{accountId}")
            .pathSegment("{addressId}")
            .buildAndExpand(accountId, addressId)
            .toUri();


        restTemplate.exchange(uri, DELETE, null, new ParameterizedTypeReference<>() {});
    }
}
