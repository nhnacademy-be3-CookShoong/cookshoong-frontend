package store.cookshoong.www.cookshoongfrontend.coupon.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.common.property.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreateCashCouponPolicyRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreateIssueCouponRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreatePercentCouponPolicyRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreateProvideCouponRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.response.SelectPolicyResponseDto;
import store.cookshoong.www.cookshoongfrontend.common.util.RestResponsePage;

/**
 * 쿠폰 관리 어뎁터.
 *
 * @author eora21 (김주호)
 * @since 2023.07.15
 */
@Component
@RequiredArgsConstructor
public class CouponManageAdapter {
    private static final String COUPON_POLICY = "/api/coupon/policies";
    private static final String STORE_ID_PARAMETER = "stores/{storeId}";
    private static final String MERCHANT_ID_PARAMETER = "merchants/{merchantId}";
    private static final String ALL = "all";
    private static final String CASH = "cash";
    private static final String PERCENT = "percent";
    private static final String POLICY_ID_PARAMETER = "{policyId}";

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
                    .fromUriString(apiProperties.getGatewayUrl())
                    .path(COUPON_POLICY)
                    .pathSegment(STORE_ID_PARAMETER)
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
                    .fromUriString(apiProperties.getGatewayUrl())
                    .path(COUPON_POLICY)
                    .pathSegment(MERCHANT_ID_PARAMETER)
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
                UriComponentsBuilder
                    .fromUriString(apiProperties.getGatewayUrl())
                    .path(COUPON_POLICY)
                    .pathSegment(ALL)
                    .build(), pageable),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<>() {
            });

        return response.getBody();
    }

    /**
     * 매장 금액 쿠폰 정책 생성.
     *
     * @param storeId the store id
     * @param request the request
     */
    public void executeStoreCashCouponPolicy(Long storeId, CreateCashCouponPolicyRequestDto request) {
        restTemplate.exchange(
            UriComponentsBuilder
                .fromUriString(apiProperties.getGatewayUrl())
                .path(COUPON_POLICY)
                .pathSegment(STORE_ID_PARAMETER, CASH)
                .buildAndExpand(storeId)
                .toUri(),
            HttpMethod.POST,
            new HttpEntity<>(request),
            new ParameterizedTypeReference<>() {
            });
    }

    /**
     * 매장 퍼센트 쿠폰 정책 생성.
     *
     * @param storeId the store id
     * @param request the request
     */
    public void executeStorePercentCouponPolicy(Long storeId, CreatePercentCouponPolicyRequestDto request) {
        restTemplate.exchange(
            UriComponentsBuilder
                .fromUriString(apiProperties.getGatewayUrl())
                .path(COUPON_POLICY)
                .pathSegment(STORE_ID_PARAMETER, PERCENT)
                .buildAndExpand(storeId)
                .toUri(),
            HttpMethod.POST,
            new HttpEntity<>(request),
            new ParameterizedTypeReference<>() {
            });
    }

    /**
     * 가맹점 금액 쿠폰 정책 생성.
     *
     * @param merchantId the merchant id
     * @param request    the request
     */
    public void executeMerchantCashCouponPolicy(Long merchantId, CreateCashCouponPolicyRequestDto request) {
        restTemplate.exchange(
            UriComponentsBuilder
                .fromUriString(apiProperties.getGatewayUrl())
                .path(COUPON_POLICY)
                .pathSegment(MERCHANT_ID_PARAMETER, CASH)
                .buildAndExpand(merchantId)
                .toUri(),
            HttpMethod.POST,
            new HttpEntity<>(request),
            new ParameterizedTypeReference<>() {
            });
    }

    /**
     * 매장 퍼센트 쿠폰 정책 생성.
     *
     * @param merchantId the merchant id
     * @param request    the request
     */
    public void executeMerchantPercentCouponPolicy(Long merchantId, CreatePercentCouponPolicyRequestDto request) {
        restTemplate.exchange(
            UriComponentsBuilder
                .fromUriString(apiProperties.getGatewayUrl())
                .path(COUPON_POLICY)
                .pathSegment(MERCHANT_ID_PARAMETER, PERCENT)
                .buildAndExpand(merchantId)
                .toUri(),
            HttpMethod.POST,
            new HttpEntity<>(request),
            new ParameterizedTypeReference<>() {
            });
    }

    /**
     * 모든 사용처 금액 쿠폰 정책 생성.
     *
     * @param request the request
     */
    public void executeUsageAllCashCouponPolicy(CreateCashCouponPolicyRequestDto request) {
        restTemplate.exchange(
            UriComponentsBuilder
                .fromUriString(apiProperties.getGatewayUrl())
                .path(COUPON_POLICY)
                .pathSegment(ALL, CASH)
                .build()
                .toUri(),
            HttpMethod.POST,
            new HttpEntity<>(request),
            new ParameterizedTypeReference<>() {
            });
    }

    /**
     * 모든 사용처 퍼센트 쿠폰 정책 생성.
     *
     * @param request the request
     */
    public void executeUsageAllPercentCouponPolicy(CreatePercentCouponPolicyRequestDto request) {
        restTemplate.exchange(
            UriComponentsBuilder
                .fromUriString(apiProperties.getGatewayUrl())
                .path(COUPON_POLICY)
                .pathSegment(ALL, PERCENT)
                .build()
                .toUri(),
            HttpMethod.POST,
            new HttpEntity<>(request),
            new ParameterizedTypeReference<>() {
            });
    }

    /**
     * 쿠폰 정책 삭제.
     *
     * @param policyId the policy id
     */
    public void eraseCouponPolicy(Long policyId) {
        restTemplate.exchange(
            UriComponentsBuilder
                .fromUriString(apiProperties.getGatewayUrl())
                .path(COUPON_POLICY)
                .pathSegment(POLICY_ID_PARAMETER)
                .buildAndExpand(policyId)
                .toUri(),
            HttpMethod.DELETE,
            null,
            new ParameterizedTypeReference<>() {
            });
    }

    /**
     * 쿠폰 발행.
     *
     * @param request the request
     */
    public void executeIssueCoupon(CreateIssueCouponRequestDto request) {
        restTemplate.exchange(
            UriComponentsBuilder
                .fromUriString(apiProperties.getGatewayUrl())
                .path("/api/coupon/issue")
                .build()
                .toUri(),
            HttpMethod.POST,
            new HttpEntity<>(request),
            new ParameterizedTypeReference<>() {
            });
    }

    /**
     * 쿠폰 발급.
     *
     * @param accountId                     the account id
     * @param createProvideCouponRequestDto the create provide coupon request dto
     */
    public void executeProvideCoupon(Long accountId, CreateProvideCouponRequestDto createProvideCouponRequestDto) {
        restTemplate.exchange(
            UriComponentsBuilder
                .fromUriString(apiProperties.getGatewayUrl())
                .path("/api/coupon/issue")
                .pathSegment("account/{accountId}")
                .buildAndExpand(accountId)
                .toUri(),
            HttpMethod.POST,
            new HttpEntity<>(createProvideCouponRequestDto),
            new ParameterizedTypeReference<>() {
            });
    }
}
