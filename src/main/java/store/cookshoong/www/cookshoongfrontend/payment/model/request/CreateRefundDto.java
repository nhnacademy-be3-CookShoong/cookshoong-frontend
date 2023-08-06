package store.cookshoong.www.cookshoongfrontend.payment.model.request;

import java.util.UUID;
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
public class CreateRefundDto {

    @NotNull
    private UUID chargeCode;
    @NotBlank
    private String refundAt;
    @NotNull
    private Long refundAmount;
    @NotBlank
    private String refundType;
}
