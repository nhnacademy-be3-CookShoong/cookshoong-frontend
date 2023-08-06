package store.cookshoong.www.cookshoongfrontend.order.controller;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.service.AccountAddressService;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartRedisDto;
import store.cookshoong.www.cookshoongfrontend.cart.service.CartService;
import store.cookshoong.www.cookshoongfrontend.coupon.model.response.SelectOwnCouponResponseDto;
import store.cookshoong.www.cookshoongfrontend.coupon.service.CouponManageService;

/**
 * 주문 controller.
 *
 * @author eora21 (김주호)
 * @since 2023.08.05
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final AccountIdAware accountIdAware;
    private final CartService cartService;
    private final AccountAddressService addressService;
    private final CouponManageService couponManageService;

    /**
     * 주문 페이지 엔드포인트.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping
    public String orderPage(Model model) {
        Long accountId = accountIdAware.getAccountId();
        String cartKey = accountId.toString();

        if (cartService.isCartIsEmpty(cartKey)) {
            return "redirect:/carts";
        }

        List<CartRedisDto> cartItems = cartService.selectCartMenuAll(cartKey);
        model.addAttribute("cartItems", cartItems);

        String storeName = cartItems.get(0).getStoreName();
        model.addAttribute("storeName", storeName);

        int totalPrice = cartService.calculateTotalPrice(cartItems);
        model.addAttribute("totalPrice", totalPrice);

        model.addAttribute("orderCode", UUID.randomUUID());

        AddressResponseDto addressResponseDto = addressService.selectAccountAddressRenewalAt(accountId);
        model.addAttribute("mainPlace", addressResponseDto.getMainPlace());
        model.addAttribute("detailPlace", addressResponseDto.getDetailPlace());

        Long storeId = cartItems.get(0).getStoreId();
        model.addAttribute("storeId", storeId);

        // 매장 배달비
        // 장바구니 금액과 배달비는 따로 해야한다, 쿠폰은 장바구니 금액애만 적용이 되어야 하므로

        return "order/order";
    }

    /**
     * 해당 주문에 적용할 수 있는 쿠폰 목록 확인.
     *
     * @param storeId the store id
     * @param model   the model
     * @return the coupon in order
     */
    @GetMapping("/{storeId}/coupon")
    public String getCouponInOrder(@PathVariable Long storeId, @RequestParam Integer totalPrice, Model model) {

        Page<SelectOwnCouponResponseDto> ownCoupons =
            couponManageService.selectOwnCoupons(accountIdAware.getAccountId(), Pageable.ofSize(20),
                true, storeId);

        model.addAttribute("buttonNumber", 5);
        model.addAttribute("ownCoupons", ownCoupons);
        model.addAttribute("totalPrice", totalPrice);

        return "order/order-coupon";
    }
}
