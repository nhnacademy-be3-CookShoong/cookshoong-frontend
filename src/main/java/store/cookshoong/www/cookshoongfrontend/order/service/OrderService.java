package store.cookshoong.www.cookshoongfrontend.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.order.adapter.OrderAdapter;
import store.cookshoong.www.cookshoongfrontend.order.model.request.CreateOrderRequestDto;
import store.cookshoong.www.cookshoongfrontend.order.model.response.CreateOrderResponseDto;

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
}
