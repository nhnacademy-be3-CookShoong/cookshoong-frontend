package store.cookshoong.www.cookshoongfrontend.shop.adapter;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.common.config.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateMenuGroupRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateMenuRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectMenuResponseDto;

/**
 * 메뉴 어뎁터.
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
     * 메뉴 그룹 등록 메서드.
     *
     * @param storeId                   매장 아이디
     * @param createMenuGroupRequestDto 메뉴 그룹 등록 Dto
     */
    public ResponseEntity<Void> executeCreateMenuGroup(Long storeId, CreateMenuGroupRequestDto createMenuGroupRequestDto) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .path("/api/stores/" + storeId + "/menu-group")
            .build()
            .toUri();

        RequestEntity<CreateMenuGroupRequestDto> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(createMenuGroupRequestDto);
        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {});
    }

    /**
     * 메뉴 등록 메서드.
     *
     * @param storeId              매장 아이디
     * @param createMenuRequestDto 메뉴 등록 Dto
     */
    public ResponseEntity<Void> executeCreateMenu(Long storeId, CreateMenuRequestDto createMenuRequestDto) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .path("/api/stores/" + storeId + "/menu")
            .build()
            .toUri();

        RequestEntity<CreateMenuRequestDto> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(createMenuRequestDto);

        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {});
    }

    /**
     * 메뉴 조회 메서드.
     *
     * @param storeId 매장 아이디
     * @return response
     */
    public List<SelectMenuResponseDto> fetchMenus(Long storeId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .path("/api/stores/" + storeId + "/menu")
            .build()
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        ResponseEntity<List<SelectMenuResponseDto>> response
            = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});

        return response.getBody();
    }
}
