package store.cookshoong.www.cookshoongfrontend.coupon.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.coupon.adapter.CouponManageAdapter;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreateCashCouponPolicyRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreateIssueCouponRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreatePercentCouponPolicyRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreateProvideCouponRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.response.SelectOwnCouponResponseDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.response.SelectPolicyResponseDto;

/**
 * 쿠폰 관리 서비스.
 *
 * @author eora21 (김주호)
 * @since 2023.07.15
 */
@Service
@RequiredArgsConstructor
public class CouponManageService {
    private final CouponManageAdapter couponManageAdapter;

    /**
     * 매장 정책 확인.
     *
     * @param pageable the pageable
     * @param storeId  the store id
     * @return the page
     */
    public Page<SelectPolicyResponseDto> selectStorePolicy(Pageable pageable, Long storeId) {
        return couponManageAdapter.fetchStoreCouponPolicy(storeId, pageable);
    }

    /**
     * 쿠폰 정책 확인.
     * 가맹점 또는 전체 정책을 가져온다.
     *
     * @param merchantId the merchant id
     * @param pageable   the pageable
     * @return the coupon policy
     */
    public Page<SelectPolicyResponseDto> selectCouponPolicy(Long merchantId, Pageable pageable) {
        if (Objects.nonNull(merchantId)) {
            return selectMerchantPolicy(pageable, merchantId);
        }

        return selectUsageAllPolicy(pageable);
    }

    private Page<SelectPolicyResponseDto> selectMerchantPolicy(Pageable pageable, Long merchantId) {
        return couponManageAdapter.fetchMerchantCouponPolicy(merchantId, pageable);
    }

    private Page<SelectPolicyResponseDto> selectUsageAllPolicy(Pageable pageable) {
        return couponManageAdapter.fetchUsageAllCouponPolicy(pageable);
    }

    /**
     * 매장 금액 쿠폰 정책 생성.
     *
     * @param request the request
     * @param storeId the store id
     */
    public void createStoreCashCouponPolicy(CreateCashCouponPolicyRequestDto request, Long storeId) {
        couponManageAdapter.executeStoreCashCouponPolicy(storeId, request);
    }

    /**
     * 매장 퍼센트 쿠폰 정책 생성.
     *
     * @param request the request
     * @param storeId the store id
     */
    public void createStorePercentCouponPolicy(CreatePercentCouponPolicyRequestDto request, Long storeId) {
        couponManageAdapter.executeStorePercentCouponPolicy(storeId, request);
    }

    /**
     * 금액 쿠폰 정책 생성.
     * 가맹점 또는 모든 사용처 정책을 생성한다.
     *
     * @param request    the request
     * @param merchantId the merchant id
     */
    public void createCashCouponPolicy(CreateCashCouponPolicyRequestDto request, Long merchantId) {
        if (Objects.nonNull(merchantId)) {
            createMerchantCashCouponPolicy(request, merchantId);
            return;
        }

        createUsageAllCashCouponPolicy(request);
    }

    /**
     * 퍼센트 쿠폰 정책 생성.
     * 가맹점 또는 모든 사용처 정책을 생성한다.
     *
     * @param request    the request
     * @param merchantId the merchant id
     */
    public void createPercentCouponPolicy(CreatePercentCouponPolicyRequestDto request, Long merchantId) {
        if (Objects.nonNull(merchantId)) {
            createMerchantPercentCouponPolicy(request, merchantId);
            return;
        }

        createUsageAllPercentCouponPolicy(request);
    }

    private void createMerchantCashCouponPolicy(CreateCashCouponPolicyRequestDto request, Long merchantId) {
        couponManageAdapter.executeMerchantCashCouponPolicy(merchantId, request);
    }

    private void createMerchantPercentCouponPolicy(CreatePercentCouponPolicyRequestDto request, Long merchantId) {
        couponManageAdapter.executeMerchantPercentCouponPolicy(merchantId, request);
    }


    private void createUsageAllCashCouponPolicy(CreateCashCouponPolicyRequestDto request) {
        couponManageAdapter.executeUsageAllCashCouponPolicy(request);
    }

    private void createUsageAllPercentCouponPolicy(CreatePercentCouponPolicyRequestDto request) {
        couponManageAdapter.executeUsageAllPercentCouponPolicy(request);
    }

    /**
     * 쿠폰 정책 삭제.
     *
     * @param policyId the policy id
     */
    public void removeCouponPolicy(Long policyId) {
        couponManageAdapter.eraseCouponPolicy(policyId);
    }

    /**
     * 쿠폰 발행.
     *
     * @param createIssueCouponRequestDto the create issue coupon request dto
     */
    public void createIssueCoupon(CreateIssueCouponRequestDto createIssueCouponRequestDto) {
        couponManageAdapter.executeIssueCoupon(createIssueCouponRequestDto);
    }

    /**
     * 쿠폰 발급.
     *
     * @param createProvideCouponRequestDto the create provide coupon request dto
     */
    public void createProvideCoupon(CreateProvideCouponRequestDto createProvideCouponRequestDto) {
        couponManageAdapter.executeProvideCoupon(createProvideCouponRequestDto);
    }

    /**
     * 내 쿠폰 확인.
     *
     * @param accountId the account id
     * @param pageable  the pageable
     * @param usable    the usable
     * @param storeId   the store id
     * @return the page
     */
    public Page<SelectOwnCouponResponseDto> selectOwnCoupons(Long accountId, Pageable pageable, Boolean usable,
                                                             Long storeId) {
        return couponManageAdapter.fetchOwnCoupons(accountId, pageable, usable, storeId);
    }
}
