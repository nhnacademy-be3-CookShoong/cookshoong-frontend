package store.cookshoong.www.cookshoongfrontend.payment.model.request.tossrefund;

import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 결제 취소에 필요한 Dto.
 *
 * @author jeongjewan
 * @since 2023.08.04
 */
@Getter
@AllArgsConstructor
public class CreateFullRefundRequestDto {

    @NotNull
    private UUID orderId;
    @NotNull
    private UUID chargeCode;
    @NotBlank
    private String paymentKey;
    @NotBlank
    private String cancelReason;
}
