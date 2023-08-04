package store.cookshoong.www.cookshoongfrontend.payment.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 결제에 대해 알아야할 정보.
 *
 * @author jeongjewan
 * @since 2023.08.03
 */
@Getter
@AllArgsConstructor
public class CreatePaymentDto {

    private String orderId;
    private String chargedAt;
    private Long chargedAmount;
    private String paymentKey;
    private String paymentType;
}
