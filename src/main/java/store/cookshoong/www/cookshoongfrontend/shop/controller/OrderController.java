package store.cookshoong.www.cookshoongfrontend.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 주문 뷰 페이지 컨트롤러.
 *
 * @author papel (윤동현)
 * @since 2023.07.19
 */
@Controller
@RequiredArgsConstructor
public class OrderController {

    /**
     * 주문 페이지 맵핑.
     *
     * @author papel
     * @since 2023.07.19
     */
    @GetMapping({"/index/store/{storeId}/order/{orderId}"})
    public String getIndexOrder(@PathVariable("storeId") Long storeId, @PathVariable("orderId") Long orderId) {
        return "index/order";
    }
}
