package store.cookshoong.www.cookshoongfrontend.payment.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 토스결제 API 에서 응답 받는 Payment 객체 DTO.
 *
 * @author jeongjewan
 * @since 2023.08.02
 */
@Getter
@Setter
public class TossResponseDto {

    private String paymentKey;
    private String orderId;
    private String orderName;
    private String status;
    private String requestedAt;
    private String approvedAt;
    private String method;
    private Long totalAmount;
    private Card card;
    private Receipt receipt;
    private EasyPay easyPay;
    private Failure failure;
    private Cancle[] cancles;

    /**
     * 카드로 결제하면 제공되는 카드 관련 정보입니다.
     */
    @Getter
    @Setter
    public static class Card {
        private Long amount;
        private String issuerCode;
        private String acuirerCode;
        private String number;
        private String installmentPlanMonths;
        private String cardType;
    }

    /**
     * 발행된 영수증 정보입니다.
     */
    @Getter
    @Setter
    public static class Receipt {
        private String url;
    }

    /**
     * 간편결제 정보입니다.
     */
    @Getter
    @Setter
    public static class EasyPay {
        private String provider;
        private Long amount;
        private Long discountAcount;
    }

    /**
     * 결제 실패 정보입니다..
     */
    @Getter
    @Setter
    public static class Failure {
        private String code;
        private String message;
    }

    /**
     * 결제 취소 이력이 담기는 배열입니다.
     */
    @Getter
    @Setter
    public static class Cancle {
        private Long cancleAmount;
        private String cancleReason;
        private String cancleAt;
        private String refundableAmount;
    }
}
