package store.cookshoong.www.cookshoongfrontend.coupon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreateCashCouponPolicyRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreateIssueCouponRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreatePercentCouponPolicyRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.model.response.SelectPolicyResponseDto;
import store.cookshoong.www.cookshoongfrontend.coupon.service.CouponManageService;

/**
 * 매장에서의 쿠폰 관리 엔드포인트.
 *
 * @author eora21 (김주호)
 * @since 2023.07.14
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/coupon/stores/{storeId}")
public class CouponManageInStoreController {
    private static final String REDIRECT_STORE_COUPON_INDEX = "redirect:/coupon/stores/";
    private final CouponManageService couponManageService;

    @ModelAttribute
    private Long storeId(@PathVariable Long storeId) {
        return storeId;
    }

    /**
     * 매장 쿠폰 정책 확인 엔드포인트.
     *
     * @param pageable the pageable
     * @param model    the model
     * @param storeId  the store id
     * @return the coupon policies
     */
    @GetMapping
    public String getStoreCouponPolicies(Pageable pageable, Model model, @ModelAttribute Long storeId) {
        Page<SelectPolicyResponseDto> policies = couponManageService.selectStorePolicy(pageable, storeId);
        model.addAttribute("policies", policies);
        model.addAttribute("buttonNumber", 5);
        model.addAttribute("storeId", storeId);
        return "coupon/coupon-store";
    }

    /**
     * 매장 금액 쿠폰 정책 생성 엔드포인트.
     *
     * @param request 쿠폰 정책 생성 dto
     * @param storeId the store id
     * @return 매장 쿠폰 view 화면
     */
    @PostMapping("/cash")
    public String postStoreCashCouponPolicies(CreateCashCouponPolicyRequestDto request, @ModelAttribute Long storeId) {
        couponManageService.createStoreCashCouponPolicy(request, storeId);
        return redirectStoreCouponIndex(storeId);
    }

    /**
     * 매장 퍼센트 쿠폰 정책 생성 엔드포인트.
     *
     * @param request 쿠폰 정책 생성 dto
     * @param storeId the store id
     * @return 매장 쿠폰 view 화면
     */
    @PostMapping("/percent")
    public String postStorePercentCouponPolicies(CreatePercentCouponPolicyRequestDto request,
                                                 @ModelAttribute Long storeId) {
        couponManageService.createStorePercentCouponPolicy(request, storeId);
        return redirectStoreCouponIndex(storeId);
    }

    /**
     * 쿠폰 정책 삭제 엔드포인트.
     *
     * @param policyId the policy id
     * @param storeId  the store id
     * @return 매장 쿠폰 view 화면
     */
    @DeleteMapping("/policies/{policyId}")
    public String deleteCouponPolicy(@PathVariable Long policyId, @ModelAttribute Long storeId) {
        couponManageService.removeCouponPolicy(policyId);
        return redirectStoreCouponIndex(storeId);
    }

    /**
     * 쿠폰 발행 엔드포인트.
     *
     * @param createIssueCouponRequestDto 쿠폰 발행 dto
     * @param storeId                     the store id
     * @return 매장 쿠폰 view 화면
     */
    @PostMapping("/issue")
    public String postCouponIssue(CreateIssueCouponRequestDto createIssueCouponRequestDto,
                                  @ModelAttribute Long storeId) {
        couponManageService.createIssueCoupon(createIssueCouponRequestDto);
        return redirectStoreCouponIndex(storeId);
    }

    /**
     * 매장 쿠폰 관리 페이지 주소를 반환하는 메서드.
     *
     * @param storeId the store id
     * @return the string
     */
    public static String redirectStoreCouponIndex(Long storeId) {
        return REDIRECT_STORE_COUPON_INDEX + storeId;
    }
}
