package store.cookshoong.www.cookshoongfrontend.shop.controller;

import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.CART_COUNT_ZERO;
import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.NO_MENU;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AccountAddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.service.AccountAddressService;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartMenuCountDto;
import store.cookshoong.www.cookshoongfrontend.cart.service.CartService;
import store.cookshoong.www.cookshoongfrontend.order.model.response.SelectOrderInStatusResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StorePaymentManageService;

/**
 * 매장 주문 완료 건에 대한 결제 내역 컨트롤러.
 *
 * @author jeongjewan
 * @since 2023.08.12
 */
@Controller
@RequiredArgsConstructor
public class StorePaymentManageController {

    private final StorePaymentManageService storePaymentManageService;
    private final AccountIdAware account;
    private final AccountAddressService accountAddressService;
    private final CartService cartService;

    /**
     * 주문이 완료된 건에 대한 결제내역.
     *
     * @param storeId  매장 아이디
     * @param pageable 페이지 처리
     * @param model    HTML 사용
     * @return 주문이 완료된 건에 대한 페이지 반환
     */
    @GetMapping("/stores/{storeId}/store-payment-manager")
    public String getSelectStoreOrderComplete(@PathVariable Long storeId, Pageable pageable,
                                              Model model) {

        Page<SelectOrderInStatusResponseDto> orderCompletePage =
                storePaymentManageService.selectOrderInComplete(storeId, pageable);

        model.addAttribute("storeId", storeId);
        model.addAttribute("orderCompletePage", orderCompletePage);
        model.addAttribute("buttonNumber", 5);

        commonInfo(model, account.getAccountId());

        return "store/payment/store-payment-manager";
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
    }
}
