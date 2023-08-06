package store.cookshoong.www.cookshoongfrontend.payment.controller;

import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import store.cookshoong.www.cookshoongfrontend.common.property.TossProperties;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.tossrefund.CreateFullRefundRequestDto;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.tossrefund.CreatePartialRefundRequestDto;
import store.cookshoong.www.cookshoongfrontend.payment.model.response.OrderCompletionInfo;
import store.cookshoong.www.cookshoongfrontend.payment.model.response.TossPaymentKeyResponseDto;
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
    @GetMapping("/toss/success")
    public String getAuthenticationSuccess(@RequestParam String paymentKey,
                                           @RequestParam UUID orderId,
                                           @RequestParam Long amount) {

        // 주문 페이지에서 redis에 storeId, memo. issueCouponCode, pointAmount 저장 (만료시간 2시간 정도)
        // 주문 생성 RestTemplate 보내고, 응답에 총 가격이 있어야 함

        // 주문 생성 후 결제 인증된 금액과 주문에 대한 금액 검증 -> 이후에 토스 승인으로 넘어간다.
        paymentService.verifyPayment(1000L, amount);

        // 카드사에서 인증이 성공된거고, 개발자 입장에서 요청만 끝난거라
        // 실제 구매자의 결제를 마무리 하기 위해서 승인 단계를 거쳐야 한다.
        // 인증된 결제를 카드사에 승인해달라는 요청 service -> 승인이 되어여 가맹점은 상품이나 서비스를 제공
        paymentService.createApproveTossPayment(paymentKey, amount, orderId);

        //TODO 추후 사용자 주문조회 페이지가 만들어지면 그쪽으로 Redirect
        return "redirect:/";
    }

    /**
     * 해당 주문에 대한 전액활불을 해주는 Controller.
     *
     * @param refundRequestDto      전액환불에 필요한 정보
     * @return                      주문 목록으로 반환
     */
    @GetMapping("/refund")
    public String getPaymentFullRefund(@Valid CreateFullRefundRequestDto refundRequestDto) {

        TossPaymentKeyResponseDto paymentKey = paymentService.selectTossPaymentKey(refundRequestDto.getOrderId());

        paymentService.createCancelTossPayment(paymentKey.getPaymentKey(), refundRequestDto);

        // 추후 환불 후 사장님 주문 내역 페이지 반환
        return "/";
    }

    /**
     * 해당 주문에 대한 부분활불을 해주는 Controller.
     *
     * @param partialRefundRequestDto       부분환불에 필요한 정보
     * @return                              주문 목록으로 반환
     */
    @GetMapping("/refund/partial")
    public String getPaymentPartialRefund(@Valid CreatePartialRefundRequestDto partialRefundRequestDto) {

        paymentService.isRefundAmountExceedsChargedAmount(
            partialRefundRequestDto.getChargeCode(), partialRefundRequestDto.getCancelAmount());

        TossPaymentKeyResponseDto paymentKey =
            paymentService.selectTossPaymentKey(partialRefundRequestDto.getOrderId());

        paymentService.createPartialCancelTossPayment(paymentKey.getPaymentKey(), partialRefundRequestDto);
        // 추후 환불 후 사장님 주문 내역 페이지 반환
        return "/";
    }
}
