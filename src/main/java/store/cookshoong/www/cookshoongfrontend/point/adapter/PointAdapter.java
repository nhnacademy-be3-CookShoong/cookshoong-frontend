package store.cookshoong.www.cookshoongfrontend.point.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.common.property.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.common.util.RestResponsePage;
import store.cookshoong.www.cookshoongfrontend.point.model.response.PointLogResponseDto;
import store.cookshoong.www.cookshoongfrontend.point.model.response.PointResponseDto;

/**
 * 포인트 어뎁터.
 *
 * @author eora21 (김주호)
 * @since 2023.08.12
 */
@Component
@RequiredArgsConstructor
public class PointAdapter {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    /**
     * 포인트 확인.
     *
     * @param accountId the account id
     * @return the point response dto
     */
    public PointResponseDto fetchPoint(Long accountId) {
        ResponseEntity<PointResponseDto> response = restTemplate.exchange(
                UriComponentsBuilder
                        .fromUriString(apiProperties.getGatewayUrl())
                        .path("/api/point/{accountId}")
                        .buildAndExpand(accountId)
                        .toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        return response.getBody();
    }

    /**
     * 포인트 로그 확인.
     *
     * @param accountId the account id
     * @param pageable  the pageable
     * @return the page
     */
    public Page<PointLogResponseDto> fetchPointLog(Long accountId, Pageable pageable) {
        ResponseEntity<RestResponsePage<PointLogResponseDto>> response = restTemplate.exchange(
                RestResponsePage.pageableToParameter(
                        UriComponentsBuilder
                                .fromUriString(apiProperties.getGatewayUrl())
                                .path("/api/point/{accountId}/log")
                                .buildAndExpand(accountId), pageable),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        return response.getBody();
    }
}
