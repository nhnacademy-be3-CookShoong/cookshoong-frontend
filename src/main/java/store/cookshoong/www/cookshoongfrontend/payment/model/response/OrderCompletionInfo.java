package store.cookshoong.www.cookshoongfrontend.payment.model.response;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 토스 결제 API 에서 필요한 DTO.
 *
 * @author jeongjewan
 * @since 2023.08.01
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderCompletionInfo {

    private UUID orderId;
    private String orderName;
    private Long amount;
    private String mainPlace;
    private String detailPlace;
    private String memo;
}
