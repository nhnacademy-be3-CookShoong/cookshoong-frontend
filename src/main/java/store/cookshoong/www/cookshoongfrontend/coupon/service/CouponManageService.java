package store.cookshoong.www.cookshoongfrontend.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.coupon.adapter.CouponManageAdapter;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreateCashCouponPolicyRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreateIssueCouponRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreatePercentCouponPolicyRequestDto;
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
     * @return the page
     */
    public Page<SelectPolicyResponseDto> selectStorePolicy(Pageable pageable) {
        // TODO: 매장 id 받아올 수 있을 때 UsageAll 아닌 Store 연결하기
        return couponManageAdapter.fetchUsageAllCouponPolicy(pageable);
    }

    /**
     * 매장 금액 쿠폰 정책 생성.
     *
     * @param request the request
     */
    public void createStoreCashCouponPolicy(CreateCashCouponPolicyRequestDto request) {
        // TODO: 매장 id 받아올 수 있을 때 UsageAll 아닌 Store 연결하기
        couponManageAdapter.executeUsageAllCashCouponPolicy(request);
    }

    /**
     * 매장 퍼센트 쿠폰 정책 생성.
     *
     * @param request the request
     */
    public void createStorePercentCouponPolicy(CreatePercentCouponPolicyRequestDto request) {
        // TODO: 매장 id 받아올 수 있을 때 UsageAll 아닌 Store 연결하기
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
}
