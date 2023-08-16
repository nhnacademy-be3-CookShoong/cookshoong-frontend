package store.cookshoong.www.cookshoongfrontend.review.adapter;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.net.URI;
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
import store.cookshoong.www.cookshoongfrontend.review.model.request.UpdateReviewResponseDto;
import store.cookshoong.www.cookshoongfrontend.review.model.response.SelectReviewResponseDto;

/**
 * 리뷰 등록, 조회, 수정에 대한 adapter.
 *
 * @author seungyeon
 * @since 2023.08.13
 */
@RequiredArgsConstructor
@Component
public class ReviewAdapter {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    public ResponseEntity<Void> executeCreateReview(Long accountId,
                                                     MultiValueMap<String, Object> mapRequest) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("accounts")
            .pathSegment("{accountId}")
            .pathSegment("review")
            .queryParam("storedAt", LocationCode.OBJECT_S.getVariable())
            .buildAndExpand(accountId)
            .toUri();

        RequestEntity<MultiValueMap<String, Object>> request = RequestEntity.post(uri)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(mapRequest);

        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });
    }
    public ResponseEntity<Void> executeUpdateReview(Long accountId,
                                                    Long reviewId, UpdateReviewResponseDto updateReviewResponseDto) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("accounts")
            .pathSegment("{accountId}")
            .pathSegment("review")
            .pathSegment("{reviewId}")
            .buildAndExpand(accountId, reviewId)
            .toUri();

        RequestEntity<UpdateReviewResponseDto> request = RequestEntity.patch(uri)
            .contentType(APPLICATION_JSON)
            .body(updateReviewResponseDto);

        return restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });
    }
    /**
     * 회원이 작성란 리뷰에 대해 조회하는 Adapter.
     *
     * @param accountId     회원 아이디
     * @param pageable      페이지 처리
     * @return              회원이 작성한 모든 리뷰를 반환
     */
    public RestResponsePage<SelectReviewResponseDto> fetchReviewByAccount(Long accountId, Pageable pageable) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("accounts")
            .pathSegment("{accountId}")
            .pathSegment("review")
            .queryParam("page", pageable.getPageNumber())
            .queryParam("size", pageable.getPageSize())
            .buildAndExpand(accountId)
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
