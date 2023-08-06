package store.cookshoong.www.cookshoongfrontend.payment.exception;

/**
 * 결제 검증에 실패했을 때 일어나는 Exception.
 *
 * @author jeongjewan
 * @since 2023.08.05
 */
public class RefundAmountExceedsChargedAmountException extends RuntimeException {

    private static final String MESSAGE = "환불 금액이 결제 금액을 넘어갔습니다. 다시 입력해주세요.";

    public RefundAmountExceedsChargedAmountException() {
        super(MESSAGE);
    }
}
