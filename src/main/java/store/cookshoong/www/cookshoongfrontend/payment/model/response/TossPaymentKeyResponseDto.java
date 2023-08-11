package store.cookshoong.www.cookshoongfrontend.payment.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Toss 에서 제공한 paymentKey 를 Charge DB 에서 받아오는 DTO.
 *
 * @author jeongjewan
 * @since 2023.08.03
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TossPaymentKeyResponseDto {

    private String paymentKey;
}
