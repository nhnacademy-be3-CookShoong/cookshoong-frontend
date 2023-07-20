package store.cookshoong.www.cookshoongfrontend.coupon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreateCashCouponPolicyRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreateIssueCouponRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreatePercentCouponPolicyRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.response.SelectPolicyResponseDto;
import store.cookshoong.www.cookshoongfrontend.coupon.service.CouponManageService;

/**
 * 쿠폰 관리 엔드포인트.
 *
 * @author eora21 (김주호)
 * @since 2023.07.14
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponManageController {
    private static final String REDIRECT_COUPON_INDEX = "redirect:/coupon";
    private final CouponManageService couponManageService;

    /**
     * 매장 쿠폰 정책 확인 엔드포인트.
     *
     * @param pageable the pageable
     * @param model    the model
     * @return the coupon policies
     */
    @GetMapping
    public String getStoreCouponPolicies(Pageable pageable, Model model) {
        Page<SelectPolicyResponseDto> policies = couponManageService.selectStorePolicy(pageable);
        model.addAttribute("policies", policies);
        model.addAttribute("buttonNumber", 5);
        return "coupon/coupon";
    }

    /**
     * 매장 금액 쿠폰 정책 생성 엔드포인트.
     *
     * @param request 쿠폰 정책 생성 dto
     * @return 매장 쿠폰 view 화면
     */
    @PostMapping("/cash")
    public String postStoreCashCouponPolicies(CreateCashCouponPolicyRequestDto request) {
        couponManageService.createStoreCashCouponPolicy(request);
        return REDIRECT_COUPON_INDEX;
    }

    /**
     * 매장 퍼센트 쿠폰 정책 생성 엔드포인트.
     *
     * @param request 쿠폰 정책 생성 dto
     * @return 매장 쿠폰 view 화면
     */
    @PostMapping("/percent")
    public String postStorePercentCouponPolicies(CreatePercentCouponPolicyRequestDto request) {
        couponManageService.createStorePercentCouponPolicy(request);
        return REDIRECT_COUPON_INDEX;
    }

    /**
     * 쿠폰 정책 삭제 엔드포인트.
     *
     * @param policyId the policy id
     * @return 매장 쿠폰 view 화면
     */
    @DeleteMapping("/policies/{policyId}")
    public String deleteCouponPolicy(@PathVariable Long policyId) {
        couponManageService.removeCouponPolicy(policyId);
        return REDIRECT_COUPON_INDEX;
    }

    /**
     * 쿠폰 발행 엔드포인트.
     *
     * @param createIssueCouponRequestDto 쿠폰 발행 dto
     * @return 매장 쿠폰 view 화면
     */
    @PostMapping("/issue")
    public String postCouponIssue(CreateIssueCouponRequestDto createIssueCouponRequestDto) {
        couponManageService.createIssueCoupon(createIssueCouponRequestDto);
        return REDIRECT_COUPON_INDEX;
    }
}
