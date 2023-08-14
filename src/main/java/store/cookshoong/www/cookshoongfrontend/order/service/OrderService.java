package store.cookshoong.www.cookshoongfrontend.order.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.order.adapter.OrderAdapter;
import store.cookshoong.www.cookshoongfrontend.order.model.request.CreateOrderRequestDto;
import store.cookshoong.www.cookshoongfrontend.order.model.response.CreateOrderResponseDto;
import store.cookshoong.www.cookshoongfrontend.order.model.response.SelectAccountOrderInStatusResponseDto;
import store.cookshoong.www.cookshoongfrontend.order.model.response.SelectOrderPossibleResponseDto;
import store.cookshoong.www.cookshoongfrontend.order.model.request.PatchOrderRequestDto;
import store.cookshoong.www.cookshoongfrontend.order.model.response.SelectOrderInStatusResponseDto;

/**
 * 주문 서비스.
 *
 * @author eora21 (김주호)
 * @since 2023.08.06
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderAdapter orderAdapter;

    /**
     * 주문 생성.
     *
     * @param createOrderRequestDto the create order request dto
     * @return the create order response dto
     */
    public CreateOrderResponseDto createService(CreateOrderRequestDto createOrderRequestDto) {
        return orderAdapter.executeOrder(createOrderRequestDto);
    }

    public SelectOrderPossibleResponseDto isOrderPossible(Long storeId, Long accountId) {
        return orderAdapter.fetchOrderPossible(storeId, accountId);
    }

    public List<SelectOrderInStatusResponseDto> selectOrderInProgress(Long storeId) {
        return orderAdapter.fetchOrderInProcess(storeId);
    }

    public void updateStoreOrderStatus(PatchOrderRequestDto patchOrderRequestDto) {
        orderAdapter.changeStoreOrderStatus(patchOrderRequestDto);
    }

    public Page<SelectAccountOrderInStatusResponseDto> selectOwnOrder(Long accountId, Pageable pageable) {
        return orderAdapter.fetchOwnOrder(accountId, pageable);
    }
}
