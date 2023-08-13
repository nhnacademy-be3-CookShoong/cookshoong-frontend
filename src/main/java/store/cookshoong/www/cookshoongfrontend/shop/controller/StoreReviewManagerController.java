package store.cookshoong.www.cookshoongfrontend.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 매장 리뷰 관리 페이지에서 사용될 컨트롤러.
 *
 * @author papel (윤동현)
 * @since 2023.08.13
 */
@Controller
@RequiredArgsConstructor
public class StoreReviewManagerController {

    @GetMapping("/stores/{storeId}/store-review-manager")
    public String businessReviewPage(
        @PathVariable("storeId") Long storeId,
        Model model) {
        model.addAttribute("storeId", storeId);
        return "store/order/store-review-manager";
    }
}
