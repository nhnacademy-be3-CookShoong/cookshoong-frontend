package store.cookshoong.www.cookshoongfrontend.coupon.adapter;

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
import store.cookshoong.www.cookshoongfrontend.coupon.model.response.SelectOwnCouponResponseDto;

/**
 * 쿠폰 어뎁터.
 *
 * @author eora21 (김주호)
 * @since 2023.08.14
 */
@Component
@RequiredArgsConstructor
public class CouponAdapter {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    /**
     * 내 유효 쿠폰 목록 확인.
     *
     * @param accountId the account id
     * @param pageable  the pageable
     * @return the page
     */
    public Page<SelectOwnCouponResponseDto> fetchOwnCoupon(Long accountId, Pageable pageable) {
        ResponseEntity<RestResponsePage<SelectOwnCouponResponseDto>> response = restTemplate.exchange(
                RestResponsePage.pageableToParameter(
                        UriComponentsBuilder
                                .fromUriString(apiProperties.getGatewayUrl())
                                .path("/api/coupon/search/{accountId}")
                                .queryParam("usable", true)
                                .buildAndExpand(accountId), pageable),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        return response.getBody();
    }
}
