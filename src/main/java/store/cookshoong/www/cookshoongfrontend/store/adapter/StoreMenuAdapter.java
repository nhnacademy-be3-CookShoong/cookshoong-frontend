package store.cookshoong.www.cookshoongfrontend.store.adapter;

import static org.springframework.http.HttpMethod.POST;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import store.cookshoong.www.cookshoongfrontend.common.property.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.store.exception.CreateMenuFailureException;
import store.cookshoong.www.cookshoongfrontend.store.model.CreateMenuGroupRequestDto;
import store.cookshoong.www.cookshoongfrontend.store.model.request.CreateMenuRequestDto;

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
}
