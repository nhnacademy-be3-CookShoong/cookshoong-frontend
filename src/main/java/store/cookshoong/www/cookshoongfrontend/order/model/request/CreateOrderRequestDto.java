package store.cookshoong.www.cookshoongfrontend.order.model.request;

import java.io.Serializable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.PaymentPageRequestDto;

/**
 * 주문에 필요한 데이터를 지정하는 dto.
 *
 * @author eora21 (김주호)
 * @since 2023.08.06
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateOrderRequestDto implements Serializable {
    private UUID orderCode;
    @Setter
    private Long accountId;
    private Long storeId;
    private String memo;
    private String deliveryAddress;
    private int deliveryCost;
    private UUID issueCouponCode;
    private Integer pointAmount;

    /**
     * Instantiates a new Order temp dto.
     *
     * @param orderId               the order code
     * @param paymentPageRequestDto the payment page request dto
     */
    public CreateOrderRequestDto(UUID orderId, PaymentPageRequestDto paymentPageRequestDto) {
        this.orderCode = orderId;
        this.storeId = paymentPageRequestDto.getStoreId();
        this.memo = paymentPageRequestDto.getMemo();
        this.issueCouponCode = paymentPageRequestDto.getCouponCode();
        this.pointAmount = paymentPageRequestDto.getPoint();
        this.deliveryAddress = paymentPageRequestDto.getMainPlace() + " " + paymentPageRequestDto.getDetailPlace();
        this.deliveryCost = paymentPageRequestDto.getDeliveryCost();
    }
}
