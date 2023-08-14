package store.cookshoong.www.cookshoongfrontend.coupon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.coupon.model.request.CreateProvideCouponRequestDto;
import store.cookshoong.www.cookshoongfrontend.coupon.service.CouponManageService;

/**
 * 발행된 쿠폰 관련 컨트롤러.
 *
 * @author eora21 (김주호)
 * @since 2023.07.25
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon/provide")
public class IssueCouponController {
    private final AccountIdAware accountIdAware;
    private final CouponManageService couponManageService;

    /**
     * 쿠폰 발급을 위한 엔드포인트.
     *
     * @param createProvideCouponRequestDto the create provide coupon request dto
     * @return the string
     */
    @PostMapping
    public String postProvideCoupon(CreateProvideCouponRequestDto createProvideCouponRequestDto) {
        try {
            createProvideCouponRequestDto.setAccountId(accountIdAware.getAccountId());
            couponManageService.createProvideCoupon(createProvideCouponRequestDto);
        } catch (HttpClientErrorException e) {
            return e.getResponseBodyAsString();
        }
        return "ok";
    }
}
