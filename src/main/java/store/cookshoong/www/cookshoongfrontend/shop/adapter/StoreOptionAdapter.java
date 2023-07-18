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
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateOptionGroupRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateOptionRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectOptionGroupResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectOptionResponseDto;

/**
 * 옵션 어뎁터.
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
     * 옵션 그룹 등록 메서드.
     *
     * @param storeId                     매장 아이디
     * @param createOptionGroupRequestDto 옵션 그룹 등록 Dto
     */
    public ResponseEntity<Void> executeCreateOptionGroup(Long storeId, CreateOptionGroupRequestDto createOptionGroupRequestDto) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("option-group")
            .buildAndExpand(storeId)
            .toUri();

        RequestEntity<CreateOptionGroupRequestDto> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(createOptionGroupRequestDto);
        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {});
    }

    /**
     * 옵션 등록 메서드.
     *
     * @param storeId                매장 아이디
     * @param optionGroupId          옵션 그룹 아이디
     * @param createOptionRequestDto 옵션 등록 Dto
     */
    public ResponseEntity<Void> executeCreateOption(Long storeId, Long optionGroupId, CreateOptionRequestDto createOptionRequestDto) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("option-group")
            .pathSegment("{optionGroupId}")
            .pathSegment("option")
            .buildAndExpand(storeId, optionGroupId)
            .toUri();

        RequestEntity<CreateOptionRequestDto> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(createOptionRequestDto);

        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {});
    }

    /**
     * 옵션 그룹 리스트 조회 메서드.
     *
     * @param storeId 매장 아이디
     * @return response
     */
    public List<SelectOptionGroupResponseDto> fetchOptionGroups(Long storeId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("option-group")
            .buildAndExpand(storeId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        ResponseEntity<List<SelectOptionGroupResponseDto>> response
            = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});

        return response.getBody();
    }

    /**
     * 옵션 그룹 조회 메서드.
     *
     * @param optionGroupId 옵션 그룹 아이디
     * @return response
     */
    public SelectOptionGroupResponseDto fetchOptionGroup(Long optionGroupId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("option-group")
            .pathSegment("{optionGroupId}")
            .buildAndExpand(optionGroupId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        ResponseEntity<SelectOptionGroupResponseDto> response
            = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});

        return response.getBody();
    }

    /**
     * 옵션 리스트 조회 메서드.
     *
     * @param storeId 매장 아이디
     * @return response
     */
    public List<SelectOptionResponseDto> fetchOptions(Long storeId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("option")
            .buildAndExpand(storeId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        ResponseEntity<List<SelectOptionResponseDto>> response
            = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});

        return response.getBody();
    }

    /**
     * 옵션 조회 메서드.
     *
     * @param optionId 매장 아이디
     * @return response
     */
    public SelectOptionResponseDto fetchOption(Long optionId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("option")
            .pathSegment("{optionId}")
            .buildAndExpand(optionId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        ResponseEntity<SelectOptionResponseDto> response
            = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});

        return response.getBody();
    }
}
