package store.cookshoong.www.cookshoongfrontend.delivery.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.delivery.model.request.ReceiveDeliveryRequestDto;

/**
 * 주문 서비스.
 *
 * @author eora21 (김주호)
 * @since 2023.08.23
 */
@Service
@RequiredArgsConstructor
public class DeliveryService {

    /**
     * Is delivery complete boolean.
     *
     * @param request the request
     * @return the boolean
     */
    public boolean isDeliveryComplete(ReceiveDeliveryRequestDto request) {
        return "A4".equals(request.getDeliveryStateCode());
    }
}
