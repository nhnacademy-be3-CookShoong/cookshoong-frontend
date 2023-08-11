package store.cookshoong.www.cookshoongfrontend.shop.adapter;

import static org.springframework.http.HttpMethod.DELETE;
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
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.common.property.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.common.util.RestResponsePage;
import store.cookshoong.www.cookshoongfrontend.file.model.LocationCode;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.UpdateStoreCategoryRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.UpdateStoreInfoRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.UpdateStoreManagerRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.UpdateStoreStatusRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllBanksResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllCategoriesResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStatusResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectStoreForUserResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectStoreInfoResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectStoresKeywordSearchResponseDto;


/**
 * 매장의 Adapter.
 * 매장 등록, 3km 이내 리스트 조회, 사용자 입장에서의 매장 조회, 사장님의 모든 매장 리스트, 매장 상태 변경.
 * 모든 카테고리 리스트 조회, 매장 상태 리스트
 *
 * @author papel (윤동현), seongyeon (유승연)
 * @since 2023.07.13
 */
@Component
@RequiredArgsConstructor
public class StoreAdapter {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    /**
     * 사업자 : 은행 리스트(셀렉트 박스를 위한) 조회.
     *
     * @return the list
     */
    public ResponseEntity<List<SelectAllBanksResponseDto>> fetchAllBanks() {

        return restTemplate.exchange(apiProperties.getGatewayUrl() + "/api/accounts/banks",
            GET,
            null,
            new ParameterizedTypeReference<>() {
            });
    }

    /**
     * 매장을 등록하는 메서드.
     *
     * @param accountId  회원아이디
     * @param mapRequest the map request
     * @return the response entity
     */
    public ResponseEntity<String> executeCreateStore(Long accountId,
                                                     MultiValueMap<String, Object> mapRequest) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("accounts")
            .pathSegment("{accountId}")
            .pathSegment("stores")
            .queryParam("storedAt", LocationCode.OBJECT_S.getVariable())
            .buildAndExpand(accountId)
            .toUri();

        RequestEntity<MultiValueMap<String, Object>> request = RequestEntity.post(uri)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(mapRequest);

        return restTemplate.exchange(request, String.class);
    }

    /**
     * 사용자 : 3km 이내 매장 리스트 조회 메서드.
     *
     * @param addressId 주소 아이디
     * @param pageable  페이지 파라미터
     * @return response rest response page
     */
    public RestResponsePage<SelectStoresKeywordSearchResponseDto> fetchStoresNotOuted(Long addressId, Pageable pageable) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("stores")
            .pathSegment("search")
            .queryParam("addressId", addressId)
            .queryParam("page", pageable.getPageNumber())
            .queryParam("size", pageable.getPageSize())
            .build()
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        ResponseEntity<RestResponsePage<SelectStoresKeywordSearchResponseDto>> response
            = restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });

        return response.getBody();
    }

    /**
     * 사용자 : 키워드 검색 매장 리스트 조회 메서드.
     *
     * @param keyword  키워드 단어
     * @param pageable 페이지 파라미터
     * @return response rest response page
     */
    public RestResponsePage<SelectStoresKeywordSearchResponseDto> fetchStoresByKeyword(String keyword, Long addressId, Pageable pageable) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("stores")
            .pathSegment("search")
            .pathSegment("keyword")
            .queryParam("keyword", keyword)
            .queryParam("addressId", addressId)
            .queryParam("page", pageable.getPageNumber())
            .queryParam("size", pageable.getPageSize())
            .build()
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        ResponseEntity<RestResponsePage<SelectStoresKeywordSearchResponseDto>> response
            = restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });

        return response.getBody();
    }


    /**
     * 매장 조회 메서드.
     *
     * @param storeId 매장 아이디
     * @return response select store for user response dto
     */
    public ResponseEntity<SelectStoreForUserResponseDto> fetchStoreForUser(Long addressId, Long storeId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("info")
            .queryParam("addressId", addressId)
            .buildAndExpand(storeId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();


        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });
    }

    /**
     * 모든 카테고리 리스트 조회.
     *
     * @return the list
     */
    public ResponseEntity<List<SelectAllCategoriesResponseDto>> fetchAllCategories() {
        return restTemplate.exchange(apiProperties.getGatewayUrl() + "/api/stores/categories",
            GET,
            null,
            new ParameterizedTypeReference<>() {
            });
    }

    /**
     * 사장님의 모든 매장들을 조회.
     *
     * @param accountId the account id
     * @return the rest response page
     */
    public ResponseEntity<List<SelectAllStoresResponseDto>> fetchAllStores(Long accountId) {
        return restTemplate.exchange(apiProperties.getGatewayUrl() + "/api/accounts/" + accountId + "/stores",
            GET,
            null,
            new ParameterizedTypeReference<>() {
            }
        );
    }

    /**
     * 매장 상태에 대한 리스트 조회.
     *
     * @return 매장 상태들
     */
    public List<SelectAllStatusResponseDto> fetchAllStatus() {
        ResponseEntity<List<SelectAllStatusResponseDto>> responseEntity =
            restTemplate.exchange(apiProperties.getGatewayUrl() + "/api/accounts/store-status",
                GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        return responseEntity.getBody();
    }

    /**
     * 매장 상태 변경에 대한 adapter.
     *
     * @param accountId  the account id
     * @param storeId    the store id
     * @param requestDto the request dto
     * @return 200 http status
     */
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

    /**
     * 매장 정보를 가져오는 dto.
     *
     * @param accountId the account id
     * @param storeId   the store id
     * @return 해당 매장 정보
     */
    public ResponseEntity<SelectStoreInfoResponseDto> fetchStoreInfo(Long accountId, Long storeId) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("accounts")
            .pathSegment("{accountId}")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .buildAndExpand(accountId, storeId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });
    }

    public ResponseEntity<Void> changeStoreManagerInfo(Long accountId, Long storeId, UpdateStoreManagerRequestDto requestDto) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("accounts")
            .pathSegment("{accountId}")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("managerInfo")
            .buildAndExpand(accountId, storeId)
            .toUri();
        RequestEntity<UpdateStoreManagerRequestDto> request = RequestEntity.patch(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(requestDto);

        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });
    }

    public ResponseEntity<Void> changeStoreInfo(Long accountId, Long storeId, UpdateStoreInfoRequestDto requestDto) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("accounts")
            .pathSegment("{accountId}")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("storeInfo")
            .buildAndExpand(accountId, storeId)
            .toUri();
        RequestEntity<UpdateStoreInfoRequestDto> request = RequestEntity.patch(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(requestDto);

        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });
    }

    public ResponseEntity<Void> changeStoreCategoryInfo(Long accountId, Long storeId, UpdateStoreCategoryRequestDto requestDto) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("accounts")
            .pathSegment("{accountId}")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("categoryInfo")
            .buildAndExpand(accountId, storeId)
            .toUri();
        RequestEntity<UpdateStoreCategoryRequestDto> request = RequestEntity.patch(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(requestDto);

        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });
    }

    public ResponseEntity<Void> changeStoreImage(Long accountId,
                                                 Long storeId,
                                                 MultiValueMap<String, Object> mapRequest) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("accounts")
            .pathSegment("{accountId}")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("storeImage")
            .buildAndExpand(accountId, storeId)
            .toUri();

        RequestEntity<MultiValueMap<String, Object>> request = RequestEntity.patch(uri)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(mapRequest);

        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });
    }
    public ResponseEntity<Void> eraseStoreImage(Long accountId,
                                                 Long storeId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("accounts")
            .pathSegment("{accountId}")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("storeImage")
            .pathSegment("delete")
            .buildAndExpand(accountId, storeId)
            .toUri();

        return restTemplate.exchange(uri,
            DELETE,
            null,
            new ParameterizedTypeReference<>() {
            });
    }

    public ResponseEntity<Void> changeStoreStatus(Long accountId,
                                                Long storeId, String option) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("accounts")
            .pathSegment("{accountId}")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("status")
            .queryParam("option", option)
            .buildAndExpand(accountId, storeId)
            .toUri();

        return restTemplate.exchange(uri,
            PATCH,
            null,
            new ParameterizedTypeReference<>() {
            });
    }
}
