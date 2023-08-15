package store.cookshoong.www.cookshoongfrontend.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.coupon.adapter.CouponAdapter;
import store.cookshoong.www.cookshoongfrontend.coupon.model.response.SelectOwnCouponResponseDto;

/**
 * 쿠폰 서비스.
 *
 * @author eora21 (김주호)
 * @since 2023.08.14
 */
@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponAdapter couponAdapter;

    public Page<SelectOwnCouponResponseDto> selectOwnCoupons(Long accountId, Pageable pageable) {
        return couponAdapter.fetchOwnCoupon(accountId, pageable);
    }
}
