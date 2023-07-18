package store.cookshoong.www.cookshoongfrontend.store.adapter;

import static org.springframework.http.HttpMethod.GET;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import store.cookshoong.www.cookshoongfrontend.common.property.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.store.model.response.SelectAllBanksResponseDto;

/**
 * 은행의 Adapter.
 *
 * @author seungyeon
 * @since 2023.07.14
 */
@RequiredArgsConstructor
@Component
public class BankAdapter {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    public List<SelectAllBanksResponseDto> fetchAllBanks(){
        HttpEntity<List<SelectAllBanksResponseDto>> httpEntity =
            restTemplate.exchange(apiProperties.getGatewayUrl() + "/api/accounts/banks",
                GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        return httpEntity.getBody();
    }
}
