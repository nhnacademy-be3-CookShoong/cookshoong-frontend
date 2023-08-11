package store.cookshoong.www.cookshoongfrontend.shop.model.response;

import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 정보를 확인하는 dto.
 *
 * @author eora21 (김주호)
 * @since 2023.08.10
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectOrderInStatusResponseDto {
    private UUID orderCode;
    private String orderStatusCode;
    private List<SelectOrderDetailMenuResponseDto> selectOrderDetails;
    private String memo;
    private UUID chargeCode;
    private int chargedAmount;
    private String paymentKey;
}
