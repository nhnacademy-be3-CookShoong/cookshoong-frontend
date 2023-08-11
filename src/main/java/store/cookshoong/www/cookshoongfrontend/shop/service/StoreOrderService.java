package store.cookshoong.www.cookshoongfrontend.shop.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.shop.adapter.StoreOrderAdapter;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.PatchOrderRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectOrderInStatusResponseDto;

/**
 * 매장 주문 관리 서비스.
 *
 * @author eora21 (김주호)
 * @since 2023.08.10
 */
@Service
@RequiredArgsConstructor
public class StoreOrderService {
    private final StoreOrderAdapter storeOrderAdapter;

    public List<SelectOrderInStatusResponseDto> selectOrderInProgress(Long storeId) {
        return storeOrderAdapter.fetchOrderInProcess(storeId);
    }

    public void updateStoreOrderStatus(PatchOrderRequestDto patchOrderRequestDto) {
        storeOrderAdapter.changeStoreOrderStatus(patchOrderRequestDto);
    }
}
