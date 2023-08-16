package store.cookshoong.www.cookshoongfrontend.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import store.cookshoong.www.cookshoongfrontend.review.model.request.CreateBusinessReviewRequestDto;
import store.cookshoong.www.cookshoongfrontend.review.model.response.SelectReviewResponseDto;
import store.cookshoong.www.cookshoongfrontend.review.model.response.SelectReviewStoreResponseDto;
import store.cookshoong.www.cookshoongfrontend.review.service.ReviewStoreService;

/**
 * 매장 리뷰 관리 페이지에서 사용될 컨트롤러.
 *
 * @author papel (윤동현)
 * @since 2023.08.13
 */
@Controller
@RequiredArgsConstructor
public class StoreReviewManagerController {
    private final ReviewStoreService reviewStoreService;

    @GetMapping("/stores/{storeId}/store-review-manager")
    public String businessReviewPage(
        Model model,
        Pageable pageable,
        @PathVariable("storeId") Long storeId) {
        Page<SelectReviewStoreResponseDto> reviewList = reviewStoreService.selectReviewByAccount(storeId, pageable);
        model.addAttribute("storeId", storeId);
        model.addAttribute("reviewList", reviewList);
        return "store/order/store-review-manager";
    }
}
