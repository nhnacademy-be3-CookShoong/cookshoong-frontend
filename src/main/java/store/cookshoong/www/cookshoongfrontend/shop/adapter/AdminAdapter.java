package store.cookshoong.www.cookshoongfrontend.shop.adapter;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.common.property.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.common.util.RestResponsePage;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateBankRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateCategoriesRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateMerchantRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.UpdateCategoryRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllBanksResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllCategoriesResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllMerchantsResponseDto;

/**
 * 은행의 Adapter.
 *
 * @author seungyeon (유승연)
 * @since 2023.07.14
 */
@RequiredArgsConstructor
@Component
public class AdminAdapter {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    public RestResponsePage<SelectAllBanksResponseDto> fetchBanksPage(Pageable pageable) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("admin")
            .pathSegment("banks")
            .queryParam("page", pageable.getPageNumber())
            .queryParam("size", pageable.getPageSize())
            .build()
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        ResponseEntity<RestResponsePage<SelectAllBanksResponseDto>> response =
            restTemplate.exchange(request, new ParameterizedTypeReference<>() {
            });

        return response.getBody();
    }

    public ResponseEntity<Void> executeCreateBank(CreateBankRequestDto createBankRequestDto) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("admin")
            .pathSegment("banks")
            .build().toUri();

        RequestEntity<CreateBankRequestDto> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(createBankRequestDto);

        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });
    }

    public RestResponsePage<SelectAllMerchantsResponseDto> fetchMerchantsPage(Pageable pageable) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("admin")
            .pathSegment("merchants")
            .queryParam("page", pageable.getPageNumber())
            .queryParam("size", pageable.getPageSize())
            .build()
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        ResponseEntity<RestResponsePage<SelectAllMerchantsResponseDto>> response =
            restTemplate.exchange(request, new ParameterizedTypeReference<>() {
            });

        return response.getBody();
    }

    public ResponseEntity<Void> executeCreateMerchant(CreateMerchantRequestDto createMerchantRequestDto) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("admin")
            .pathSegment("merchants")
            .build().toUri();

        RequestEntity<CreateMerchantRequestDto> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(createMerchantRequestDto);

        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });
    }

    public ResponseEntity<Void> eraseMerchant(Long merchantId){
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("admin")
            .pathSegment("merchants")
            .pathSegment("{merchantId}")
            .buildAndExpand(merchantId)
            .toUri();

        return restTemplate.exchange(uri,
            DELETE,
            null,
            new ParameterizedTypeReference<>() {});
    }
    public RestResponsePage<SelectAllCategoriesResponseDto> fetchCategoriesPage(Pageable pageable) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("admin")
            .pathSegment("categories")
            .queryParam("page", pageable.getPageNumber())
            .queryParam("size", pageable.getPageSize())
            .build()
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        ResponseEntity<RestResponsePage<SelectAllCategoriesResponseDto>> response =
            restTemplate.exchange(request, new ParameterizedTypeReference<>() {
            });

        return response.getBody();
    }

    public ResponseEntity<Void> executeCreateCategories(CreateCategoriesRequestDto createMerchantRequestDto) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("admin")
            .pathSegment("categories")
            .build().toUri();

        RequestEntity<CreateCategoriesRequestDto> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(createMerchantRequestDto);

        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });
    }
    public ResponseEntity<Void> eraseCategory(String categoryCode){
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("admin")
            .pathSegment("categories")
            .pathSegment("{categoryCode}")
            .buildAndExpand(categoryCode)
            .toUri();

        return restTemplate.exchange(uri,
            DELETE,
            null,
            new ParameterizedTypeReference<>() {});
    }
    public ResponseEntity<Void> changeCategory(UpdateCategoryRequestDto updateCategoryRequestDto,
                                                       String categoryCode) {
        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("admin")
            .pathSegment("categories")
            .pathSegment("{categoryCode}")
            .buildAndExpand(categoryCode)
            .toUri();

        RequestEntity<UpdateCategoryRequestDto> request = RequestEntity.put(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(updateCategoryRequestDto);

        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });
    }
}
