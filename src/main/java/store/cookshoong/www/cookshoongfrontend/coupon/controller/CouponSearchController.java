package store.cookshoong.www.cookshoongfrontend.coupon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import store.cookshoong.www.cookshoongfrontend.coupon.model.response.SelectOwnCouponResponseDto;
import store.cookshoong.www.cookshoongfrontend.coupon.service.CouponManageService;

/**
 * 발급된 쿠폰을 검색해 가져오는 컨트롤러.
 *
 * @author eora21 (김주호)
 * @since 2023.08.05
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/coupon/search")
public class CouponSearchController {
    private final CouponManageService couponManageService;

    /**
     * 소유한 쿠폰 목록 확인.
     *
     * @param accountId the account id
     * @param pageable  the pageable
     * @param usable    the usable
     * @param storeId   the store id
     * @return the own coupons
     */
    @GetMapping("/{accountId}")
    public Page<SelectOwnCouponResponseDto> getOwnCoupons(@PathVariable Long accountId, Pageable pageable,
                                                          @RequestParam(required = false) Boolean usable,
                                                          @RequestParam(required = false) Long storeId) {
        return couponManageService.selectOwnCoupons(accountId, pageable, usable, storeId);
    }
}
