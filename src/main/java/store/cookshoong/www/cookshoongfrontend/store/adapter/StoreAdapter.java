package store.cookshoong.www.cookshoongfrontend.store.adapter;

import static org.springframework.http.HttpMethod.POST;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import store.cookshoong.www.cookshoongfrontend.common.config.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.store.exception.CreateStoreFailureException;
import store.cookshoong.www.cookshoongfrontend.store.model.CreateStoreRequestDto;

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
    public void createStore(Long accountId, CreateStoreRequestDto createStoreRequestDto) {

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
}
