package store.cookshoong.www.cookshoongfrontend.shop.adapter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import store.cookshoong.www.cookshoongfrontend.common.config.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.shop.exception.CreateMenuFailureException;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateMenuGroupRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateMenuRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresNotOutedResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectMenuResponseDto;
import store.cookshoong.www.cookshoongfrontend.util.RestResponsePage;

/**
 * 메뉴의 Adapter.
 *
 * @author papel
 * @since 2023.07.13
 */
@Component
@RequiredArgsConstructor
public class StoreMenuAdapter {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    /**
     * 메뉴 그룹을 등록하는 메서드.
     *
     * @param storeId                                매장아이디
     * @param createMenuGroupRequestDto              사업자가 메뉴 그룹을 등록하는 Dto
     */
    public void executeCreateMenuGroup(Long storeId, CreateMenuGroupRequestDto createMenuGroupRequestDto) {

        HttpEntity<CreateMenuGroupRequestDto> httpEntity =
            new HttpEntity<>(createMenuGroupRequestDto);

        ResponseEntity<Void> response =
            restTemplate.exchange(
                apiProperties.getGatewayUrl() +
                    "/api/stores/" +
                    storeId +
                    "/menu-group",
                POST,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });

        if(!response.getStatusCode().is2xxSuccessful()) {
            throw new CreateMenuFailureException(response.getStatusCode());
        }
    }

    /**
     * 메뉴를 등록하는 메서드.
     *
     * @param storeId                           매장아이디
     * @param createMenuRequestDto              사업자가 메뉴를 등록하는 Dto
     */
    public void executeCreateMenu(Long storeId, CreateMenuRequestDto createMenuRequestDto) {

        HttpEntity<CreateMenuRequestDto> httpEntity =
            new HttpEntity<>(createMenuRequestDto);

        ResponseEntity<Void> response =
            restTemplate.exchange(
                apiProperties.getGatewayUrl() +
                    "/api/stores/" +
                    storeId +
                    "/menu",
            POST,
            httpEntity,
            new ParameterizedTypeReference<>() {
            });

        if(!response.getStatusCode().is2xxSuccessful()) {
            throw new CreateMenuFailureException(response.getStatusCode());
        }
    }

    public List<SelectMenuResponseDto> fetchMenus(Long storeId) {
        ResponseEntity<List<SelectMenuResponseDto>> responseEntity =
            restTemplate.exchange(
                apiProperties.getGatewayUrl() +
                    "/api/stores/" +
                    storeId +
                    "/menu",
                GET,
                null,
                new ParameterizedTypeReference<>() {
                }
            );
        return responseEntity.getBody();
    }
}
