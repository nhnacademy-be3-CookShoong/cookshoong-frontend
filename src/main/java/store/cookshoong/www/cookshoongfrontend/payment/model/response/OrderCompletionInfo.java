package store.cookshoong.www.cookshoongfrontend.payment.model.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 토스 결제 API 에서 필요한 DTO.
 *
 * @author jeongjewan
 * @since 2023.08.01
 */
@Getter
@AllArgsConstructor
public class OrderCompletionInfo {

    private UUID orderId;
    private String orderName;
    private Long amount;
}
