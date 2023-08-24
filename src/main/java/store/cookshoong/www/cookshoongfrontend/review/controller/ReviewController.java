package store.cookshoong.www.cookshoongfrontend.review.controller;

import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.CART_COUNT_ZERO;
import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.NO_MENU;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountService;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AccountAddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.service.AccountAddressService;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartMenuCountDto;
import store.cookshoong.www.cookshoongfrontend.cart.service.CartService;
import store.cookshoong.www.cookshoongfrontend.review.model.request.CreateReviewRequestDto;
import store.cookshoong.www.cookshoongfrontend.review.model.request.UpdateReviewResponseDto;
import store.cookshoong.www.cookshoongfrontend.review.model.response.SelectReviewResponseDto;
import store.cookshoong.www.cookshoongfrontend.review.service.ReviewService;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreService;

/**
 * 리뷰 등록, 수정, 조회를 위한 controller.
 *
 * @author seungyeon (유승연)
 * @since 2023.08.13
 */
@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final AccountIdAware accountIdAware;
    private final AccountAddressService accountAddressService;
    private final AccountService accountService;
    private final CartService cartService;
    private final StoreService storeService;

    /**
     * 회원 리뷰 페이지 진입.
     *
     * @param model                   the model
     * @param pageable                the pageable
     * @param createReviewRequestDto  the create review request dto
     * @param updateReviewResponseDto the update review response dto
     * @return the my review
     */
    @GetMapping("/my-review")
    public String getMyReview(Model model, @PageableDefault Pageable pageable,
                              CreateReviewRequestDto createReviewRequestDto,
                              UpdateReviewResponseDto updateReviewResponseDto) {

        List<AccountAddressResponseDto> accountAddresses =
            accountAddressService.selectAccountAddressAll(accountIdAware.getAccountId());
        Page<SelectReviewResponseDto> accountReviews =
            reviewService.selectReviewByAccount(accountIdAware.getAccountId(), pageable);

        model.addAttribute("accountReviews", accountReviews);
        model.addAttribute("createReviewRequestDto", createReviewRequestDto);
        model.addAttribute("accountAddresses", accountAddresses);
        model.addAttribute("updateReviewRequestDto", updateReviewResponseDto);
        model.addAttribute("accountInfo", accountService.selectAccountMypageInfo(accountIdAware.getAccountId()));

        commonInfo(model, accountIdAware.getAccountId());

        return "account/my-review";
    }

    /**
     * 사용자 : 리뷰 등록을 위한 controller.
     *
     * @param createReviewRequestDto the create review request dto
     * @param bindingResult          the binding result
     * @param reviewImages           the review images
     * @return the string
     */
    @PostMapping("/review")
    public String postCreateReview(
        @Valid @ModelAttribute("createReviewRequestDto") CreateReviewRequestDto createReviewRequestDto,
        BindingResult bindingResult,
        @RequestPart("reviewImage") List<MultipartFile> reviewImages) {

        if (bindingResult.hasErrors()) {
            return "redirect:/my-review";
        }

        Long accountId = accountIdAware.getAccountId();
        reviewService.createReview(accountId, createReviewRequestDto, reviewImages);
        return "redirect:/my-review";
    }

    /**
     * 사용자 : 리뷰 수정을 위한 controller.
     *
     * @param updateReviewResponseDto the update review response dto
     * @param bindingResult           the binding result
     * @return the string
     */
    @PatchMapping("/review")
    public String patchAccountReview(@Valid @ModelAttribute("updateReviewResponseDto") UpdateReviewResponseDto updateReviewResponseDto,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/my-review";
        }
        Long accountId = accountIdAware.getAccountId();
        reviewService.updateReview(accountId, updateReviewResponseDto.getReviewId(), updateReviewResponseDto);
        return "redirect:/my-review";
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

        List<SelectAllStoresResponseDto> businessStoreList = storeService.selectStores(accountId);
        model.addAttribute("businessStoreList", businessStoreList);
    }
}
