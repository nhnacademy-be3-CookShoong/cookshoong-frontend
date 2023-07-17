package store.cookshoong.www.cookshoongfrontend.store.adapter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import store.cookshoong.www.cookshoongfrontend.account.model.vo.AccountIdOnly;
import store.cookshoong.www.cookshoongfrontend.common.config.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.store.exception.CreateStoreFailureException;
import store.cookshoong.www.cookshoongfrontend.store.model.request.CreateStoreRequestDto;
import store.cookshoong.www.cookshoongfrontend.store.model.request.UpdateStoreStatusRequestDto;
import store.cookshoong.www.cookshoongfrontend.store.model.response.SelectAllCategoriesResponseDto;
import store.cookshoong.www.cookshoongfrontend.store.model.response.SelectAllStatusResponseDto;
import store.cookshoong.www.cookshoongfrontend.store.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.util.RestResponsePage;


/**
 * 매장의 Adapter.
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
     * 매장을 등록하는 메서드.
     *
     * @param accountId                         회원아이디
     * @param createStoreRequestDto             사업자가 매장을 등록하는 Dto
     */
    public void executeCreateStore(Long accountId, CreateStoreRequestDto createStoreRequestDto) {

        HttpEntity<CreateStoreRequestDto> httpEntity =
            new HttpEntity<>(createStoreRequestDto);

        ResponseEntity<Void> response = restTemplate.exchange(apiProperties.getGatewayUrl() + "/api/accounts/" + accountId + "/stores",
            POST,
            httpEntity,
            new ParameterizedTypeReference<>() {
            });

        if(!response.getStatusCode().is2xxSuccessful()) {
            throw new CreateStoreFailureException(response.getStatusCode());
        }
    }

    public List<SelectAllCategoriesResponseDto> fetchAllCategories(){
        ResponseEntity<List<SelectAllCategoriesResponseDto>> responseEntity =
            restTemplate.exchange(apiProperties.getGatewayUrl() + "/api/accounts/categories",
                GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        return responseEntity.getBody();
    }

    public RestResponsePage<SelectAllStoresResponseDto> fetchAllStores(Long accountId,
                                                                       Pageable pageable){
        ResponseEntity<RestResponsePage<SelectAllStoresResponseDto>> responseEntity=
            restTemplate.exchange(RestResponsePage.pageableToParameter(apiProperties.getGatewayUrl()+"/api/accounts/"+accountId+"/stores", pageable),
                GET,
                null,
                new ParameterizedTypeReference<>() {
                }
            );
        return responseEntity.getBody();
    }

    public List<SelectAllStatusResponseDto> fetchAllStatus(){
        ResponseEntity<List<SelectAllStatusResponseDto>> responseEntity =
            restTemplate.exchange(apiProperties.getGatewayUrl() + "/api/accounts/store-status",
                GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        return responseEntity.getBody();
    }

    public HttpStatus changeStatus(Long accountId, Long storeId, UpdateStoreStatusRequestDto requestDto){
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        HttpEntity<UpdateStoreStatusRequestDto> httpEntity = new HttpEntity<>(requestDto);
        ResponseEntity<Void> responseEntity=
            restTemplate.exchange(apiProperties.getGatewayUrl() + "/api/accounts/" + accountId + "/stores/" + storeId + "/status",
                PATCH,
                httpEntity,
                new ParameterizedTypeReference<>() {
                }
            );
        return responseEntity.getStatusCode();
    }
}
