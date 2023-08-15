package store.cookshoong.www.cookshoongfrontend.review.adapter;

import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.common.property.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.file.model.LocationCode;

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
                                                     UUID orderCode,
                                                     MultiValueMap<String, Object> mapRequest) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("accounts")
            .pathSegment("{accountId}")
            .pathSegment("orders")
            .pathSegment("{orderCode}")
            .pathSegment("review")
            .queryParam("storedAt", LocationCode.OBJECT_S.getVariable())
            .buildAndExpand(accountId, orderCode)
            .toUri();

        RequestEntity<MultiValueMap<String, Object>> request = RequestEntity.post(uri)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(mapRequest);

        return restTemplate.exchange(request, new ParameterizedTypeReference<Void>() {
        });
    }

}
