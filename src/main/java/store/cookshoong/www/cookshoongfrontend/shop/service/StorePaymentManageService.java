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

    /**
     * 주문이 완료된 건들에 대해 모두 가져오는 메서드.
     *
     * @param storeId       매장 아이디
     * @param pageable      페이징 처리
     * @return              모든 완료된 주문
     */
    public Page<SelectOrderInStatusResponseDto> selectOrderInComplete(Long storeId, Pageable pageable) {

        return storeOrderAdapter.fetchOrderInComplete(storeId, pageable);
    }
}
