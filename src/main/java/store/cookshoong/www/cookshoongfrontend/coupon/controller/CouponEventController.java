package store.cookshoong.www.cookshoongfrontend.coupon.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import store.cookshoong.www.cookshoongfrontend.coupon.model.response.SelectProvableCouponPolicyResponseDto;
import store.cookshoong.www.cookshoongfrontend.coupon.service.CouponManageService;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllMerchantsResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.MerchantService;

/**
 * 이벤트 발급 가능한 쿠폰들의 목록을 확인할 수 있는 컨트롤러.
 *
 * @author eora21 (김주호)
 * @since 2023.08.15
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/event")
public class CouponEventController {
    private final CouponManageService couponManageService;
    private final MerchantService merchantService;

    /**
     * 이벤트 쿠폰 확인 엔드포인트.
     *
     * @param merchantId the merchant id
     * @param model      the model
     * @return the event coupons
     */
    @GetMapping
    public String getEventCoupons(@RequestParam(required = false) Long merchantId, Model model) {
        List<SelectAllMerchantsResponseDto> merchants = merchantService.selectAllMerchants();
        model.addAttribute("merchants", merchants);

        List<SelectProvableCouponPolicyResponseDto> policies =
            couponManageService.selectProvableCouponPolicy(merchantId);
        model.addAttribute("policies", policies);

        return "coupon/coupon-event";
    }
}
