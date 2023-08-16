package store.cookshoong.www.cookshoongfrontend.shop.controller;

import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.CART_COUNT_ZERO;
import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.NO_MENU;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AccountAddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.service.AccountAddressService;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartMenuCountDto;
import store.cookshoong.www.cookshoongfrontend.cart.service.CartService;
import store.cookshoong.www.cookshoongfrontend.order.model.request.PatchOrderRequestDto;
import store.cookshoong.www.cookshoongfrontend.order.model.response.SelectOrderInStatusResponseDto;
import store.cookshoong.www.cookshoongfrontend.order.service.OrderService;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.tossrefund.CreateFullRefundRequestDto;
import store.cookshoong.www.cookshoongfrontend.payment.service.PaymentService;

/**
 * 매장 주문 관리 페이지 컨트롤러.
 *
 * @author eora21 (김주호)
 * @since 2023.08.10
 */
@Controller
@RequiredArgsConstructor
public class StoreOrderManagerController {
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final AccountIdAware account;
    private final AccountAddressService accountAddressService;
    private final CartService cartService;

    /**
     * 매장 주문 확인.
     *
     * @param storeId the store id
     * @param model   the model
     * @return the string
     */
    @GetMapping("/stores/{storeId}/store-order-manager")
    public String selectStoreOrder(@PathVariable Long storeId, Model model) {
        List<SelectOrderInStatusResponseDto> orders = orderService.selectOrderInProgress(storeId);
        model.addAttribute("orders", orders);

        commonInfo(model, account.getAccountId());

        return "store/order/store-order-manager";
    }

    /**
     * 매장 주문 상태 변경.
     *
     * @param patchOrderRequestDto the patch order request dto
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/stores/order/status")
    public void patchStoreOrderStatus(@RequestBody PatchOrderRequestDto patchOrderRequestDto) {
        orderService.updateStoreOrderStatus(patchOrderRequestDto);
    }

    /**
     * 주문 목록 내에서 환불.
     *
     * @param createFullRefundRequestDto the create full refund request dto
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/stores/order/refund")
    public void postRefundInOrder(@RequestBody CreateFullRefundRequestDto createFullRefundRequestDto) {

        paymentService.createCancelTossPayment(createFullRefundRequestDto);
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
