package store.cookshoong.www.cookshoongfrontend.payment.service;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.payment.adapter.paymentapi.TossPaymentAdapter;
import store.cookshoong.www.cookshoongfrontend.payment.adapter.paymentbackapi.ChargeAdapter;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.CreatePaymentDto;
import store.cookshoong.www.cookshoongfrontend.payment.model.response.TossResponseDto;
import store.cookshoong.www.cookshoongfrontend.payment.util.PaymentType;

/**
 * 결제에 관련된 Service.
 *
 * @author jeongjewan
 * @since 2023.08.02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final TossPaymentAdapter tossPaymentAdapter;
    private final ChargeAdapter chargeAdapter;

    /**
     * 구매자가 인증을 성공적으로 마치면 가맹점이 설정한 성공 URL 로 리다이텍트 되는 Controller 메서드.
     *
     * @param paymentKey        결제 인증 후 전달되는 결제 Key
     * @param orderId           결제 인증 후 전달되는 주문 아이디
     * @param amount            결제 인증 후 전달되는 결제할 금액
     */
    public void createApproveTossPayment(String paymentKey, Long amount,
                                         UUID orderId) {

        TossResponseDto tossResponseDto =
            tossPaymentAdapter.requestApproveTossPayment(
                    paymentKey, amount, orderId
            );

        chargeAdapter.executePayment(new CreatePaymentDto(
                tossResponseDto.getOrderId(), tossResponseDto.getApprovedAt(),
                tossResponseDto.getTotalAmount(), tossResponseDto.getPaymentKey(),
                PaymentType.TOSS.getType()));
    }
}
