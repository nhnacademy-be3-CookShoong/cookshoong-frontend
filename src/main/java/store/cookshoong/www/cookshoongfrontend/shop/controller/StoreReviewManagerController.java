package store.cookshoong.www.cookshoongfrontend.shop.controller;

import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.CART_COUNT_ZERO;
import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.NO_MENU;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AccountAddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.service.AccountAddressService;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartMenuCountDto;
import store.cookshoong.www.cookshoongfrontend.cart.service.CartService;
import store.cookshoong.www.cookshoongfrontend.review.model.request.CreateBusinessReviewRequestDto;
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
    private final AccountIdAware account;
    private final AccountAddressService accountAddressService;
    private final CartService cartService;

    @GetMapping("/stores/{storeId}/store-review-manager")
    public String businessReviewPage(
        Model model,
        Pageable pageable,
        @PathVariable("storeId") Long storeId,
        CreateBusinessReviewRequestDto createBusinessReviewRequestDto) {
        Page<SelectReviewStoreResponseDto> reviewList = reviewStoreService.selectReviewByAccount(storeId, pageable);
        model.addAttribute("storeId", storeId);
        model.addAttribute("createBusinessReviewRequestDto", createBusinessReviewRequestDto);
        model.addAttribute("reviewList", reviewList);

        commonInfo(model, account.getAccountId());

        return "store/order/store-review-manager";
    }

    private void commonInfo(Model model, Long accountId) {

        List<AccountAddressResponseDto> accountAddresses =
            accountAddressService.selectAccountAddressAll(accountId);
        CartMenuCountDto cartMenuCountDto =
            cartService.selectCartMenuCountAll(String.valueOf(accountId));

        if (cartService.existMenuInCartRedis(String.valueOf(accountId), NO_MENU)) {
            model.addAttribute("count", CART_COUNT_ZERO);
        } else {
            model.addAttribute("count", cartMenuCountDto.getCount());
        }
        model.addAttribute("accountAddresses", accountAddresses);
    }
}
