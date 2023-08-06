package store.cookshoong.www.cookshoongfrontend.payment.exception;

/**
 * 결제 검증에 실패했을 때 일어나는 Exception.
 *
 * @author jeongjewan
 * @since 2023.08.05
 */
public class PaymentAmountMismatchException extends RuntimeException {

    private static final String MESSAGE = "결제 금액과 주문 금액이 일치하지 않아 검증에서 실패하였습니다.";

    public PaymentAmountMismatchException() {
        super(MESSAGE);
    }
}
