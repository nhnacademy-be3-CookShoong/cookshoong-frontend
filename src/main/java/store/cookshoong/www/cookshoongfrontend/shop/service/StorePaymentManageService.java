package store.cookshoong.www.cookshoongfrontend.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.shop.adapter.StoreOrderAdapter;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectOrderInStatusResponseDto;

/**
 * 주문이 완료된 건에 대한 모든 주문.
 *
 * @author jeongjewan
 * @since 2023.08.11
 */
@Service
@RequiredArgsConstructor
public class StorePaymentManageService {

    private final StoreOrderAdapter storeOrderAdapter;

    public Page<SelectOrderInStatusResponseDto> selectOrderInComplete(Long storeId, Pageable pageable) {

        return storeOrderAdapter.fetchOrderInComplete(storeId, pageable);
    }
}
