package store.cookshoong.www.cookshoongfrontend.review.adapter;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.common.property.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.common.util.RestResponsePage;
import store.cookshoong.www.cookshoongfrontend.file.model.LocationCode;
import store.cookshoong.www.cookshoongfrontend.review.model.response.SelectReviewResponseDto;

/**
 * 매장에 대한 리뷰 조회 adapter.
 *
 * @author seungyeon
 * @since 2023.08.13
 */
@RequiredArgsConstructor
@Component
public class ReviewStoreAdapter {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    /**
     * 회원이 작성한 리뷰에 대해 조회하는 Adapter.
     *
     * @param storeId       매장 아이디
     * @param pageable      페이지 처리
     * @return              회원이 작성한 모든 리뷰를 반환
     */
    public RestResponsePage<SelectReviewResponseDto> fetchReviewByStore(Long storeId, Pageable pageable) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("store")
            .pathSegment("{storeId}")
            .pathSegment("review")
            .queryParam("page", pageable.getPageNumber())
            .queryParam("size", pageable.getPageSize())
            .buildAndExpand(storeId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .accept(APPLICATION_JSON)
            .build();

        ResponseEntity<RestResponsePage<SelectReviewResponseDto>> response =
            restTemplate.exchange(request, new ParameterizedTypeReference<>() {
            });

        return response.getBody();
    }
}
