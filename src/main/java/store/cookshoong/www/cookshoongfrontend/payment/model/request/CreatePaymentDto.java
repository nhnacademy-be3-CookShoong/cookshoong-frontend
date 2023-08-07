package store.cookshoong.www.cookshoongfrontend.payment.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotBlank
    private String orderId;
    @NotBlank
    private String chargedAt;
    @NotNull
    private Long chargedAmount;
    @NotBlank
    private String paymentKey;
    @NotBlank
    private String paymentType;
    @NotBlank
    private String cartKey;
}
