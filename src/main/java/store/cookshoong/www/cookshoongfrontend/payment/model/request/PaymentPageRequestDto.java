package store.cookshoong.www.cookshoongfrontend.payment.model.request;

import java.io.Serializable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 결제 페이지에 넘겨줄 dto.
 *
 * @author eora21 (김주호)
 * @since 2023.08.06
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentPageRequestDto implements Serializable {
    private Long storeId;
    private Integer price;
    private String mainPlace;
    private String detailPlace;
    private String memo;
    private UUID couponCode;
    private Integer point;
}
