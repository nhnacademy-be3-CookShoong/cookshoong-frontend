package store.cookshoong.www.cookshoongfrontend.delivery.model.request;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 콜백 수신 시 사용되는 dto.
 *
 * @author eora21 (김주호)
 * @since 2023.08.23
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceiveDeliveryRequestDto {
    private String callbackId;
    private String deliveryStateCode;

    public UUID getOrderCode() {
        return UUID.fromString(callbackId.split("_")[1]);
    }
}
