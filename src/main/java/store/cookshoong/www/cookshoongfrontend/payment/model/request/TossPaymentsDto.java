package store.cookshoong.www.cookshoongfrontend.payment.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Toss Payments 결제 API 필요한 DTO.
 *
 * @author jeongjewan
 * @since 2023.08.01
 */
@Getter
@AllArgsConstructor
public class TossPaymentsDto {

    private String clientId;
    private String secret;
    private String successUrl;
    private String failUrl;
}
