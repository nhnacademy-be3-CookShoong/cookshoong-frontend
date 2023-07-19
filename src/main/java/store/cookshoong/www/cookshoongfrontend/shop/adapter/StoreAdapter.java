package store.cookshoong.www.cookshoongfrontend.shop.adapter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.common.config.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateStoreRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.UpdateStoreStatusRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllCategoriesResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStatusResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectStoresNotOutedResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.util.RestResponsePage;


/**
 * 매장 어뎁터.
 *
 * @author papel
 * @since 2023.07.13
 */
@Component
@RequiredArgsConstructor
public class StoreAdapter {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    /**
     * 매장 등록 메서드.
     *
     * @param accountId             회원 아이디
     * @param createStoreRequestDto 매장 등록 Dto
     * @return response
     */
    public ResponseEntity<Void> executeCreateStore(Long accountId, CreateStoreRequestDto createStoreRequestDto) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .path("/api/accounts/" + accountId + "/stores")
            .build()
            .toUri();

        RequestEntity<CreateStoreRequestDto> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(createStoreRequestDto);

        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {});
    }

    /**
     * 3km 이내 매장 조회 메서드.
     *
     * @param addressId 주소 아이디
     * @param pageable  페이지 파라미터
     * @return response
     */
    public RestResponsePage<SelectStoresNotOutedResponseDto> fetchStoresNotOuted(Long addressId, Pageable pageable) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .path("/api/accounts/customer/" + addressId + "/stores")
            .queryParam("page", pageable.getPageNumber())
            .queryParam("size", pageable.getPageSize())
            .build()
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        ResponseEntity<RestResponsePage<SelectStoresNotOutedResponseDto>> response
            = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});

        return response.getBody();
    }











    public List<SelectAllCategoriesResponseDto> fetchAllCategories() {
        ResponseEntity<List<SelectAllCategoriesResponseDto>> responseEntity =
            restTemplate.exchange(apiProperties.getGatewayUrl() + "/api/accounts/categories",
                GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        return responseEntity.getBody();
    }

    public RestResponsePage<SelectAllStoresResponseDto> fetchAllStores(Long accountId,
                                                                       Pageable pageable) {
        ResponseEntity<RestResponsePage<SelectAllStoresResponseDto>> responseEntity =
            restTemplate.exchange(RestResponsePage.pageableToParameter(apiProperties.getGatewayUrl() + "/api/accounts/" + accountId + "/stores", pageable),
                GET,
                null,
                new ParameterizedTypeReference<>() {
                }
            );
        return responseEntity.getBody();
    }

    public List<SelectAllStatusResponseDto> fetchAllStatus() {
        ResponseEntity<List<SelectAllStatusResponseDto>> responseEntity =
            restTemplate.exchange(apiProperties.getGatewayUrl() + "/api/accounts/store-status",
                GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        return responseEntity.getBody();
    }

    public HttpStatus changeStatus(Long accountId, Long storeId, UpdateStoreStatusRequestDto requestDto) {

        HttpEntity<UpdateStoreStatusRequestDto> httpEntity = new HttpEntity<>(requestDto);
        ResponseEntity<Void> responseEntity =
            restTemplate.exchange(apiProperties.getGatewayUrl() + "/api/accounts/" + accountId + "/stores/" + storeId + "/status",
                PATCH,
                httpEntity,
                new ParameterizedTypeReference<>() {
                }
            );
        return responseEntity.getStatusCode();
    }

}
