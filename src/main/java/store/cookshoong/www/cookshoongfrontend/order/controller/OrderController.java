package store.cookshoong.www.cookshoongfrontend.order.controller;

import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.CART_COUNT_ZERO;
import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.EMPTY_CART;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AccountAddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.service.AccountAddressService;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartMenuCountDto;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartRedisDto;
import store.cookshoong.www.cookshoongfrontend.cart.service.CartService;
import store.cookshoong.www.cookshoongfrontend.coupon.model.response.SelectOwnCouponResponseDto;
import store.cookshoong.www.cookshoongfrontend.coupon.service.CouponManageService;
import store.cookshoong.www.cookshoongfrontend.order.model.response.SelectOrderPossibleResponseDto;
import store.cookshoong.www.cookshoongfrontend.order.service.OrderService;
import store.cookshoong.www.cookshoongfrontend.point.service.PointService;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreService;

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
    private final AccountAddressService accountAddressService;
    private final CouponManageService couponManageService;
    private final OrderService orderService;
    private final PointService pointService;
    private final StoreService storeService;

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
        Long storeId = cartItems.get(0).getStoreId();

        if (!orderService.isOrderPossible(storeId, accountId).isOrderPossible()) {
            return "redirect:/carts";
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("storeId", storeId);

        String storeName = cartItems.get(0).getStoreName();
        model.addAttribute("storeName", storeName);

        Integer deliveryCost = cartItems.get(0).getDeliveryCost();
        model.addAttribute("deliveryCost", deliveryCost);

        int totalPrice = cartService.calculateTotalPrice(cartItems);
        model.addAttribute("totalPrice", totalPrice);

        model.addAttribute("orderCode", UUID.randomUUID());

        AddressResponseDto addressResponseDto = accountAddressService.selectAccountAddressRenewalAt(accountId);
        model.addAttribute("mainPlace", addressResponseDto.getMainPlace());
        model.addAttribute("detailPlace", addressResponseDto.getDetailPlace());

        int point = pointService.selectPoint(accountId);
        model.addAttribute("point", point);

        commonInfo(model, accountIdAware.getAccountId());

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
    public String getCouponInOrder(@PathVariable Long storeId, @RequestParam Integer totalPrice, Model model,
                                   Pageable pageable) {

        Page<SelectOwnCouponResponseDto> ownCoupons =
            couponManageService.selectOwnCoupons(accountIdAware.getAccountId(), pageable,
                true, storeId);

        model.addAttribute("buttonNumber", 5);
        model.addAttribute("ownCoupons", ownCoupons);
        model.addAttribute("totalPrice", totalPrice);

        return "order/order-coupon";
    }

    private void commonInfo(Model model, Long accountId) {

        List<AccountAddressResponseDto> accountAddresses =
            accountAddressService.selectAccountAddressAll(accountId);
        CartMenuCountDto cartMenuCountDto =
            cartService.selectCartMenuCountAll(String.valueOf(accountId));

        if (cartService.existMenuInCartRedis(String.valueOf(accountId), EMPTY_CART)) {
            model.addAttribute("count", CART_COUNT_ZERO);
        } else {
            model.addAttribute("count", cartMenuCountDto.getCount());
        }

        model.addAttribute("accountAddresses", accountAddresses);

        List<SelectAllStoresResponseDto> businessStoreList = storeService.selectStores(accountId);
        model.addAttribute("businessStoreList", businessStoreList);

    }
}
