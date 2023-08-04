package store.cookshoong.www.cookshoongfrontend.payment.controller;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import store.cookshoong.www.cookshoongfrontend.common.property.TossProperties;
import store.cookshoong.www.cookshoongfrontend.payment.model.response.OrderCompletionInfo;
import store.cookshoong.www.cookshoongfrontend.payment.service.PaymentService;

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

    private final TossProperties tossProperties;
    private final PaymentService paymentService;

    /**
     * 주문에서 결제로 넘어오는 페이지.
     *
     * @param model         HTML 로 보낼 데이터
     * @return              결제 페이지 반
     */
    @GetMapping
    public String getPaymentPage(Model model) {

        //TODO 추후 주문 코드로 주문에서 결제로 넘어온 데이터 Dto 에 맞게 가져오기
        OrderCompletionInfo orderCompletionInfo = new OrderCompletionInfo(
            UUID.randomUUID(), "맛난 후라이드 외2개", 48000L);


        model.addAttribute("order", orderCompletionInfo);
        model.addAttribute("toss", tossProperties.tossPaymentsDto());

        return "payment/toss-api";
    }

    /**
     * 구매자가 인증을 성공적으로 마치면 가맹점이 설정한 성공 URL 로 리다이텍트 되는 Controller 메서드.
     *
     * @param paymentKey        결제 인증 후 전달되는 결제 Key
     * @param orderId           결제 인증 후 전달되는 주문 아이디
     * @param amount            결제 인증 후 전달되는 결제할 금액
     * @return                  회원 주문 조회 페이지로 반환
     */
    @GetMapping("/success")
    public String getAuthenticationSuccess(@RequestParam String paymentKey,
                                           @RequestParam UUID orderId,
                                           @RequestParam Long amount) {
        // 카드사에서 인증이 성공된거고, 개발자 입장에서 요청만 끝난거라
        // 실제 구매자의 결제를 마무리 하기 위해서 승인 단계를 거쳐야 한다.
        // 인증된 결제를 카드사에 승인해달라는 요청 service -> 승인이 되어여 가맹점은 상품이나 서비스를 제공
        paymentService.createApproveTossPayment(paymentKey, amount, orderId);

        //TODO 추후 사용자 주문조회 페이지가 만들어지면 그쪽으로 Redirect
        return "redirect:/";
    }

}
