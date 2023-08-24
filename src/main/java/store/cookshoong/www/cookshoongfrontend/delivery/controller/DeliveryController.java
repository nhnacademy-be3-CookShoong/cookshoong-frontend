package store.cookshoong.www.cookshoongfrontend.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.cookshoong.www.cookshoongfrontend.delivery.model.request.ReceiveDeliveryRequestDto;
import store.cookshoong.www.cookshoongfrontend.delivery.service.DeliveryService;
import store.cookshoong.www.cookshoongfrontend.order.model.request.PatchOrderRequestDto;
import store.cookshoong.www.cookshoongfrontend.order.service.OrderService;

/**
 * 주문 컨트롤러.
 *
 * @author eora21 (김주호)
 * @since 2023.08.23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;
    private final OrderService orderService;

    /**
     * 배달 완료 시 주문 상태 변경.
     *
     * @param request the request
     */
    @PostMapping
    public void postDelivery(@RequestBody ReceiveDeliveryRequestDto request) {
        if (!deliveryService.isDeliveryComplete(request)) {
            return;
        }

        orderService.updateStoreOrderStatus(
                new PatchOrderRequestDto(request.getOrderCode(), PatchOrderRequestDto.StatusCode.COMPLETE));
    }
}
