package store.cookshoong.www.cookshoongfrontend.payment.service;

import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.payment.adapter.paymentapi.TossPaymentAdapter;
import store.cookshoong.www.cookshoongfrontend.payment.adapter.paymentbackapi.PaymentAdapter;
import store.cookshoong.www.cookshoongfrontend.payment.exception.PaymentAmountMismatchException;
import store.cookshoong.www.cookshoongfrontend.payment.exception.RefundAmountExceedsChargedAmountException;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.CreatePaymentDto;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.CreateRefundDto;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.tossrefund.CreateFullRefundRequestDto;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.tossrefund.CreatePartialRefundRequestDto;
import store.cookshoong.www.cookshoongfrontend.payment.model.response.TossPaymentKeyResponseDto;
import store.cookshoong.www.cookshoongfrontend.payment.model.response.TossResponseDto;
import store.cookshoong.www.cookshoongfrontend.payment.util.PaymentType;
import store.cookshoong.www.cookshoongfrontend.payment.util.RefundType;

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
    private final PaymentAdapter paymentAdapter;
    private final AccountIdAware account;
    private static final String CART = "cartKey=";

    /**
     * 구매자가 인증을 성공적으로 마치면 가맹점이 설정한 성공 URL 로 리다이텍트 되는 Controller 메서드.
     *
     * @param paymentKey 결제 인증 후 전달되는 결제 Key
     * @param amount     결제 인증 후 전달되는 결제할 금액
     * @param orderId    결제 인증 후 전달되는 주문 아이디
     * @param couponCode 사용하는 쿠폰 코드
     * @param point      사용하는 포인트
     */
    public void createApproveTossPayment(String paymentKey, Long amount,
                                         UUID orderId, UUID couponCode, Integer point) {

        TossResponseDto tossResponseDto =
            tossPaymentAdapter.requestApproveTossPayment(
                    paymentKey, amount, orderId
            );

        paymentAdapter.executePayment(new CreatePaymentDto(
                tossResponseDto.getOrderId(), tossResponseDto.getApprovedAt(),
                tossResponseDto.getTotalAmount(), tossResponseDto.getPaymentKey(),
                PaymentType.TOSS.getType(), CART + account.getAccountId(), couponCode, point));
    }

    /**
     * 주문에 대한 전액 환불.
     *
     * @param paymentKey        paymentKey
     * @param refundRequestDto  취소 사유에 대한 Dto
     */
    public void createCancelTossPayment(String paymentKey, CreateFullRefundRequestDto refundRequestDto) {

        TossResponseDto tossResponseDto =
            tossPaymentAdapter.requestCancelTossPayment(paymentKey, refundRequestDto.getCancelReason());

        paymentAdapter.executeRefund(
            new CreateRefundDto(
                refundRequestDto.getChargeCode(), tossResponseDto.getApprovedAt(),
                tossResponseDto.getCancels()[0].getCancelAmount(), RefundType.FULL.getType()));
    }

    /**
     * 주문에 대한 부분 환불.
     *
     * @param paymentKey        paymentKey
     * @param refundRequestDto  취소 사유와 환분에 데한 Dto
     */
    public void createPartialCancelTossPayment(String paymentKey, CreatePartialRefundRequestDto refundRequestDto) {

        TossResponseDto tossResponseDto =
            tossPaymentAdapter.requestPartialCancelTossPayment(paymentKey,
                refundRequestDto.getCancelReason(), refundRequestDto.getCancelAmount());

        paymentAdapter.executeRefund(
            new CreateRefundDto(refundRequestDto.getChargeCode(), tossResponseDto.getApprovedAt(),
                tossResponseDto.getCancels()[0].getCancelAmount(), RefundType.PARTIAL.getType()));
    }

    /**
     * 응답 받은 paymentKey 를 전달하는 메서드.
     *
     * @param orderCode     주문코드
     * @return              paymentKey
     */
    public TossPaymentKeyResponseDto selectTossPaymentKey(UUID orderCode) {

        return paymentAdapter.fetchTossPaymentKey(orderCode);
    }

    /**
     * 토스에서 결제 인증된 금액과 주문된 금액이 일치한지 검사하는 메서드.   <br>
     * 검증 실패 시 예외 발생
     *
     * @param orderAmount       주문 금액
     * @param amount            결제 금액
     */
    public void verifyPayment(Long orderAmount, Long amount) {

        if (!Objects.equals(orderAmount, amount)) {
            throw new PaymentAmountMismatchException();
        }
    }

    /**
     * 환불 금액이 결제 금액을 넘어가는지 검증하는 메서드.
     *
     * @param chargeCode        결제 코드
     * @param refundAmount      환불 금액
     */
    public void isRefundAmountExceedsChargedAmount(UUID chargeCode, Integer refundAmount) {

        if (paymentAdapter.fetchIsRefundAmountExceedsChargedAmount(chargeCode, refundAmount)) {
            throw new RefundAmountExceedsChargedAmountException();
        }
    }
}
