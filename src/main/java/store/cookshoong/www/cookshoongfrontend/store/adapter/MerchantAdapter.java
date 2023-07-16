package store.cookshoong.www.cookshoongfrontend.store.adapter;

import static org.springframework.http.HttpMethod.GET;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import store.cookshoong.www.cookshoongfrontend.common.config.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.store.model.response.SelectAllMerchantsResponseDto;

/**
 * 가맹점 Adapter.
 *
 * @author seungyeon
 * @since 2023.07.14
 */
@Component
@RequiredArgsConstructor
public class MerchantAdapter {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    public List<SelectAllMerchantsResponseDto> fetchAllMerchants(){
        HttpEntity<List<SelectAllMerchantsResponseDto>> httpEntity =
            restTemplate.exchange(apiProperties.getGatewayUrl() + "/api/accounts/merchants",
                GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        return httpEntity.getBody();
    }
}
