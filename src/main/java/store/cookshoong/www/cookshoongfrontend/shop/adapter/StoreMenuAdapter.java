package store.cookshoong.www.cookshoongfrontend.shop.adapter;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.common.property.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateMenuGroupRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateMenuRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectMenuGroupResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectMenuResponseDto;

/**
 * 메뉴 어뎁터.
 *
 * @author papel (윤동현)
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
    public ResponseEntity<Void> executeCreateMenuGroup(Long storeId,
                                                       CreateMenuGroupRequestDto createMenuGroupRequestDto) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("menu-group")
            .buildAndExpand(storeId)
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
    public ResponseEntity<Void> executeCreateMenu(Long storeId, CreateMenuRequestDto createMenuRequestDto,
                                                  MultipartFile menuImage) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("menu")
            .buildAndExpand(storeId)
            .toUri();

        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("requestDto", createMenuRequestDto);
        multiValueMap.add("menuImage", menuImage.getResource());

        RequestEntity<MultiValueMap<String, Object>> request = RequestEntity.post(uri)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(multiValueMap);

        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {});
    }

    /**
     * 메뉴 그룹 리스트 조회 메서드.
     *
     * @param storeId 매장 아이디
     * @return response
     */
    public List<SelectMenuGroupResponseDto> fetchMenuGroups(Long storeId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("menu-group")
            .buildAndExpand(storeId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        ResponseEntity<List<SelectMenuGroupResponseDto>> response
            = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});

        return response.getBody();
    }

    /**
     * 메뉴 그룹 조회 메서드.
     *
     * @param storeId 매장 아이디
     * @param menuGroupId 메뉴 그룹 아이디
     * @return response
     */
    public SelectMenuGroupResponseDto fetchMenuGroup(Long storeId, Long menuGroupId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("menu-group")
            .pathSegment("{menuGroupId}")
            .buildAndExpand(storeId, menuGroupId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        ResponseEntity<SelectMenuGroupResponseDto> response
            = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});

        return response.getBody();
    }

    /**
     * 메뉴 리스트 조회 메서드.
     *
     * @param storeId 매장 아이디
     * @return response
     */
    public List<SelectMenuResponseDto> fetchMenus(Long storeId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("menu")
            .buildAndExpand(storeId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        ResponseEntity<List<SelectMenuResponseDto>> response
            = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});

        return response.getBody();
    }

    /**
     * 메뉴 조회 메서드.
     *
     * @param storeId 매장 아이디
     * @param menuId 메뉴 아이디
     * @return response
     */
    public SelectMenuResponseDto fetchMenu(Long storeId, Long menuId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("menu")
            .pathSegment("{menuId}")
            .buildAndExpand(storeId, menuId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        ResponseEntity<SelectMenuResponseDto> response
            = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});

        return response.getBody();
    }

    /**
     * 메뉴 그룹 삭제 메서드.
     *
     * @param storeId     매장 아이디
     * @param menuGroupId 메뉴 그룹 아이디
     */
    public ResponseEntity<Void> executeDeleteMenuGroup(Long storeId, Long menuGroupId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("menu-group")
            .pathSegment("{menu-group}")
            .buildAndExpand(storeId, menuGroupId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.delete(uri).build();
        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {});
    }

    /**
     * 메뉴 삭제 메서드.
     *
     * @param storeId 매장 아이디
     * @param menuId  메뉴 아이디
     */
    public ResponseEntity<Void> executeDeleteMenu(Long storeId, Long menuId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("stores")
            .pathSegment("{storeId}")
            .pathSegment("menu")
            .pathSegment("{menuId}")
            .buildAndExpand(storeId, menuId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.delete(uri).build();
        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {});
    }
}
