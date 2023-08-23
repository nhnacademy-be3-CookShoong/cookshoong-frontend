package store.cookshoong.www.cookshoongfrontend.coupon.controller;

import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.CART_COUNT_ZERO;
import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.NO_MENU;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AccountAddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.service.AccountAddressService;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartMenuCountDto;
import store.cookshoong.www.cookshoongfrontend.cart.service.CartService;
import store.cookshoong.www.cookshoongfrontend.coupon.model.response.SelectProvableCouponPolicyResponseDto;
import store.cookshoong.www.cookshoongfrontend.coupon.service.CouponManageService;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllMerchantsResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.MerchantService;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreService;

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
    private final AccountAddressService accountAddressService;
    private final AccountIdAware account;
    private final CartService cartService;
    private final StoreService storeService;

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

        commonInfo(model, account.getAccountId());

        return "coupon/coupon-event";
    }


    private void commonInfo(Model model, Long accountId) {

        List<AccountAddressResponseDto> accountAddresses =
            accountAddressService.selectAccountAddressAll(accountId);
        CartMenuCountDto cartMenuCountDto =
            cartService.selectCartMenuCountAll(String.valueOf(accountId));

        if (cartService.existMenuInCartRedis(String.valueOf(accountId), NO_MENU)) {
            model.addAttribute("count", CART_COUNT_ZERO);
        } else {
            model.addAttribute("count", cartMenuCountDto.getCount());
        }

        model.addAttribute("accountAddresses", accountAddresses);

        List<SelectAllStoresResponseDto> businessStoreList = storeService.selectStores(accountId);
        model.addAttribute("businessStoreList", businessStoreList);

    }
}
