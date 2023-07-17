package store.cookshoong.www.cookshoongfrontend.shop.adapter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import store.cookshoong.www.cookshoongfrontend.common.config.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.shop.exception.CreateMenuFailureException;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateOptionGroupRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateOptionRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectMenuResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectOptionResponseDto;

/**
 * 옵션의 Adapter.
 *
 * @author papel
 * @since 2023.07.13
 */
@Component
@RequiredArgsConstructor
public class StoreOptionAdapter {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    /**
     * 옵션 그룹을 등록하는 메서드.
     *
     * @param storeId                     매장아이디
     * @param createOptionGroupRequestDto 사업자가 옵션 그룹을 등록하는 Dto
     */
    public void executeCreateOptionGroup(Long storeId, CreateOptionGroupRequestDto createOptionGroupRequestDto) {

        HttpEntity<CreateOptionGroupRequestDto> httpEntity =
            new HttpEntity<>(createOptionGroupRequestDto);

        ResponseEntity<Void> response =
            restTemplate.exchange(
                apiProperties.getGatewayUrl() +
                    "/api/stores/" +
                    storeId +
                    "/option-group",
                POST,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CreateMenuFailureException(response.getStatusCode());
        }
    }

    /**
     * 옵션을 등록하는 메서드.
     *
     * @param storeId                매장아이디
     * @param optionGroupId          옵션 그룹아이디
     * @param createOptionRequestDto 사업자가 옵션를 등록하는 Dto
     */
    public void executeCreateOption(Long storeId, Long optionGroupId, CreateOptionRequestDto createOptionRequestDto) {

        HttpEntity<CreateOptionRequestDto> httpEntity =
            new HttpEntity<>(createOptionRequestDto);

        ResponseEntity<Void> response =
            restTemplate.exchange(
                apiProperties.getGatewayUrl() +
                    "/api/stores/" +
                    storeId +
                    "/option-group/" +
                    optionGroupId +
                    "/option",
                POST,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CreateMenuFailureException(response.getStatusCode());
        }
    }

    public List<SelectOptionResponseDto> fetchOptions(Long storeId) {
        ResponseEntity<List<SelectOptionResponseDto>> responseEntity =
            restTemplate.exchange(
                apiProperties.getGatewayUrl() +
                    "/api/stores/" +
                    storeId +
                    "/option",
                GET,
                null,
                new ParameterizedTypeReference<>() {
                }
            );
        return responseEntity.getBody();
    }
}
