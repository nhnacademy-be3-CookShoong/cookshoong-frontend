package store.cookshoong.www.cookshoongfrontend.payment.controller;

import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.CART_COUNT_ZERO;
import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.EMPTY_CART;

import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AccountAddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.service.AccountAddressService;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartMenuCountDto;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartRedisDto;
import store.cookshoong.www.cookshoongfrontend.cart.service.CartService;
import store.cookshoong.www.cookshoongfrontend.common.property.TossProperties;
import store.cookshoong.www.cookshoongfrontend.order.model.request.CreateOrderRequestDto;
import store.cookshoong.www.cookshoongfrontend.order.model.response.CreateOrderResponseDto;
import store.cookshoong.www.cookshoongfrontend.order.service.OrderService;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.PaymentPageRequestDto;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.tossrefund.CreateFullRefundRequestDto;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.tossrefund.CreatePartialRefundRequestDto;
import store.cookshoong.www.cookshoongfrontend.payment.model.response.OrderCompletionInfo;
import store.cookshoong.www.cookshoongfrontend.payment.service.PaymentService;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreService;

/**
 * 결제와 관련된 Controller.
 *
 * @author jeongjewan
 * @since 2023.08.01
 */
@Slf4j
@Controller
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final AccountIdAware accountIdAware;
    private final TossProperties tossProperties;
    private final PaymentService paymentService;
    private final AccountAddressService accountAddressService;
    private final OrderService orderService;
    private final CartService cartService;
    private final StoreService storeService;

    /**
     * 주문에서 결제로 넘어오는 페이지.
     *
     * @param paymentPageRequestDto the payment page request dto
     * @param model                 HTML 로 보낼 데이터
     * @param httpSession           the http session
     * @return 결제 페이지 반
     */
    @PostMapping
    public String getPaymentPage(PaymentPageRequestDto paymentPageRequestDto, Model model, HttpSession httpSession) {
        UUID orderCode = UUID.randomUUID();

        String cartKey = accountIdAware.getAccountId().toString();
        List<CartRedisDto> cartRedis = cartService.selectCartMenuAll(cartKey);
        CartRedisDto cartRedisDto = cartRedis.get(0);
        String orderName = cartRedisDto.getMenu().getMenuName();

        Integer totalCount = cartRedis.stream()
            .map(CartRedisDto::getCount)
            .reduce(Integer::sum)
            .orElseThrow(RuntimeException::new);

        if (1 < totalCount) {
            orderName += " 외 " + (totalCount - 1) + "개";
        }

        OrderCompletionInfo orderCompletionInfo = new OrderCompletionInfo(
            orderCode, orderName, (long) paymentPageRequestDto.getPrice(),
            paymentPageRequestDto.getMainPlace(), paymentPageRequestDto.getDetailPlace(),
            paymentPageRequestDto.getMemo());


        List<AccountAddressResponseDto> accountAddresses =
            accountAddressService.selectAccountAddressAll(accountIdAware.getAccountId());

        model.addAttribute("accountAddresses", accountAddresses);

        httpSession.setAttribute("paymentPageRequestDto", paymentPageRequestDto);

        model.addAttribute("order", orderCompletionInfo);
        model.addAttribute("toss", tossProperties.tossPaymentsDto());

        commonInfo(model, accountIdAware.getAccountId());
        return "payment/toss-api";
    }

    /**
     * 구매자가 인증을 성공적으로 마치면 가맹점이 설정한 성공 URL 로 리다이텍트 되는 Controller 메서드.
     *
     * @param paymentKey            결제 인증 후 전달되는 결제 Key
     * @param orderId               결제 인증 후 전달되는 주문 아이디
     * @param amount                결제 인증 후 전달되는 결제할 금액
     * @param paymentPageRequestDto the payment page request dto
     * @return 회원 주문 조회 페이지로 반환
     */
    @GetMapping("/toss/success")
    public String getAuthenticationSuccess(@RequestParam String paymentKey,
                                           @RequestParam UUID orderId,
                                           @RequestParam Long amount,
                                           @SessionAttribute PaymentPageRequestDto paymentPageRequestDto) {
        CreateOrderRequestDto createOrderRequestDto = new CreateOrderRequestDto(orderId, paymentPageRequestDto);

        createOrderRequestDto.setAccountId(accountIdAware.getAccountId());
        CreateOrderResponseDto createOrderResponseDto = orderService.createService(createOrderRequestDto);

        paymentService.verifyPayment((long) createOrderResponseDto.getTotalPrice(), amount);
        paymentService.createApproveTossPayment(paymentKey, amount, orderId, paymentPageRequestDto.getCouponCode(),
            paymentPageRequestDto.getPoint(), paymentPageRequestDto.getDiscountAmount());

        return "redirect:/my-orders";
    }

    /**
     * 결제 및 환불에 대한 실패 Controller.
     *
     * @return 주문 목록으로 반환
     */
    @PostMapping("/toss/fail")
    public String getPaymentFail(@RequestParam String code,
                                 @RequestParam String message,
                                 Model model) {

        model.addAttribute("code", code);
        model.addAttribute("message", message);

        commonInfo(model, accountIdAware.getAccountId());

        return "payment/error/toss-error";
    }

    /**
     * 해당 주문에 대한 전액활불을 해주는 Controller.
     *
     * @param refundRequestDto 전액환불에 필요한 정보
     * @return 주문 목록으로 반환
     */
    @PostMapping("/refund/{storeId}")
    public String getPaymentFullRefund(@PathVariable Long storeId, Model model,
                                       @Valid CreateFullRefundRequestDto refundRequestDto) {


        paymentService.createCancelTossPayment(refundRequestDto);

        return "redirect:/stores/" + storeId + "/store-payment-manager";
    }

    /**
     * 해당 주문에 대한 부분활불을 해주는 Controller.
     *
     * @param partialRefundRequestDto 부분환불에 필요한 정보
     * @return 주문 목록으로 반환
     */
    @PostMapping("/refund/partial/{storeId}")
    public String getPaymentPartialRefund(@PathVariable Long storeId, Model model,
                                          @Valid CreatePartialRefundRequestDto partialRefundRequestDto) {

        paymentService.isRefundAmountExceedsChargedAmountVerify(
            partialRefundRequestDto.getChargeCode(), partialRefundRequestDto.getCancelAmount());

        paymentService.createPartialCancelTossPayment(partialRefundRequestDto);

        return "redirect:/stores/" + storeId + "/store-payment-manager";
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
