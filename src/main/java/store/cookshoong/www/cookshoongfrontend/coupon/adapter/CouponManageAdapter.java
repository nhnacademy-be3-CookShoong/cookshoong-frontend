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
import store.cookshoong.www.cookshoongfrontend.common.config.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.coupon.model.response.SelectPolicyResponseDto;
import store.cookshoong.www.cookshoongfrontend.util.RestResponsePage;

/**
 * 쿠폰 관리 어뎁터.
 *
 * @author eora21(김주호)
 * @since 2023.07.15
 */
@Component
@RequiredArgsConstructor
public class CouponManageAdapter {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    /**
     * 매장 쿠폰 정책 확인.
     *
     * @param storeId  the store id
     * @param pageable the pageable
     * @return the page
     */
    public Page<SelectPolicyResponseDto> fetchStoreCouponPolicy(Long storeId, Pageable pageable) {
        ResponseEntity<RestResponsePage<SelectPolicyResponseDto>> response = restTemplate.exchange(
            RestResponsePage.pageableToParameter(
                UriComponentsBuilder
                    .fromUriString("http://localhost:8080")
                    .path("/api/coupon/policies/stores/{storeId}")
                    .buildAndExpand(storeId), pageable),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<>() {
            });

        return response.getBody();
    }

    /**
     * 가맹점 쿠폰 정책 확인.
     *
     * @param merchantId the merchant id
     * @param pageable   the pageable
     * @return the page
     */
    public Page<SelectPolicyResponseDto> fetchMerchantCouponPolicy(Long merchantId, Pageable pageable) {
        ResponseEntity<RestResponsePage<SelectPolicyResponseDto>> response = restTemplate.exchange(
            RestResponsePage.pageableToParameter(
                UriComponentsBuilder
                    .fromUriString("http://localhost:8080")
                    .path("/api/coupon/policies/merchants/{merchantId}")
                    .buildAndExpand(merchantId), pageable),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<>() {
            });

        return response.getBody();
    }

    /**
     * 모든 사용처 쿠폰 정책 확인.
     *
     * @param pageable the pageable
     * @return the page
     */
    public Page<SelectPolicyResponseDto> fetchUsageAllCouponPolicy(Pageable pageable) {
        ResponseEntity<RestResponsePage<SelectPolicyResponseDto>> response = restTemplate.exchange(
            RestResponsePage.pageableToParameter(
                "http://localhost:8080/api/coupon/policies/all", pageable),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<>() {
            });

        return response.getBody();
    }
}
