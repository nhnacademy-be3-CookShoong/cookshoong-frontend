package store.cookshoong.www.cookshoongfrontend.coupon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import store.cookshoong.www.cookshoongfrontend.coupon.model.response.SelectPolicyResponseDto;
import store.cookshoong.www.cookshoongfrontend.coupon.service.CouponManageService;

/**
 * 쿠폰 관리 엔드포인트.
 *
 * @author eora21(김주호)
 * @since 2023.07.14
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponManageController {
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
        return "/coupon/coupon";
    }
}
