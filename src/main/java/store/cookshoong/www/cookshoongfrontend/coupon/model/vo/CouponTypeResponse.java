package store.cookshoong.www.cookshoongfrontend.coupon.model.vo;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 쿠폰 타입 객체를 묶기 위한 interface.
 *
 * @author eora21 (김주호)
 * @since 2023.07.16
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = CouponTypeCashVo.class, name = "cash"),
    @JsonSubTypes.Type(value = CouponTypePercentVo.class, name = "percent")
})
public interface CouponTypeResponse {
    int getDiscountPrice(int totalPrice);

    int getMinimumOrderPrice();
}
