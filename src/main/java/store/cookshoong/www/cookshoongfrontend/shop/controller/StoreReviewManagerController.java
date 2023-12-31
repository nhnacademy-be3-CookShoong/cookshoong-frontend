package store.cookshoong.www.cookshoongfrontend.shop.controller;

import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.CART_COUNT_ZERO;
import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.EMPTY_CART;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AccountAddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.service.AccountAddressService;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartMenuCountDto;
import store.cookshoong.www.cookshoongfrontend.cart.service.CartService;
import org.springframework.web.bind.annotation.PostMapping;
import store.cookshoong.www.cookshoongfrontend.review.model.request.CreateBusinessReviewRequestDto;
import store.cookshoong.www.cookshoongfrontend.review.model.request.UpdateReplyResponseDto;
import store.cookshoong.www.cookshoongfrontend.review.model.response.SelectReviewStoreResponseDto;
import store.cookshoong.www.cookshoongfrontend.review.service.ReviewBusinessStoreService;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreService;

/**
 * 매장 리뷰 관리 페이지에서 사용될 컨트롤러.
 *
 * @author papel (윤동현)
 * @since 2023.08.13
 */
@Controller
@RequiredArgsConstructor
public class StoreReviewManagerController {
    private final AccountIdAware account;
    private final AccountAddressService accountAddressService;
    private final CartService cartService;
    private final ReviewBusinessStoreService reviewBusinessStoreService;
    private final StoreService storeService;

    /**
     * 사업자 : 매장에 달린 리뷰 및 본인이 작성한 답글 조회 가능.
     *
     * @param model                          the model
     * @param pageable                       the pageable
     * @param storeId                        the store id
     * @param createBusinessReviewRequestDto the create business review request dto
     * @param updateReplyResponseDto         the update reply response dto
     * @return the business review page
     */
    @GetMapping("/stores/{storeId}/store-review-manager")
    public String getBusinessReviewPage(
        Model model,
        Pageable pageable,
        @PathVariable("storeId") Long storeId,
        CreateBusinessReviewRequestDto createBusinessReviewRequestDto,
        UpdateReplyResponseDto updateReplyResponseDto) {
        Page<SelectReviewStoreResponseDto> reviewList = reviewBusinessStoreService.selectReviewByStore(storeId, pageable);
        model.addAttribute("storeId", storeId);
        model.addAttribute("createBusinessReviewRequestDto", createBusinessReviewRequestDto);
        model.addAttribute("updateReplyResponseDto", updateReplyResponseDto);
        model.addAttribute("reviewList", reviewList);

        commonInfo(model, account.getAccountId());

        return "store/order/store-review-manager";
    }


    /**
     * 사업자 : 매장에 작성된 리뷰에 답글을 등록.
     *
     * @param storeId       the store id
     * @param dto           the dto
     * @param bindingResult the binding result
     * @return the string
     */
    @PostMapping("/stores/{storeId}/store-review-manager/reply")
    public String postBusinessReviewReply(@PathVariable("storeId") Long storeId,
                                          @Valid @ModelAttribute("createBusinessReviewRequestDto") CreateBusinessReviewRequestDto dto,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/stores/" + storeId + "/store-review-manager";
        }
        reviewBusinessStoreService.createAccountReviewByReviewReply(dto.getReviewId(), dto);
        return "redirect:/stores/" + storeId + "/store-review-manager";
    }

    /**
     * 사업자 : 본인이 작성한 답글을 수정.
     *
     * @param storeId                the store id
     * @param updateReplyResponseDto the update reply response dto
     * @param bindingResult          the binding result
     * @return the string
     */
    @PatchMapping("/stores/{storeId}/store-review-manager/reply")
    public String patchBusinessReviewReply(@PathVariable("storeId") Long storeId,
                                           @Valid @ModelAttribute("updateReplyResponseDto") UpdateReplyResponseDto updateReplyResponseDto,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/stores/" + storeId + "/store-review-manager";
        }
        reviewBusinessStoreService.updateAccountReviewByReviewReply(updateReplyResponseDto.getReplyId(), updateReplyResponseDto);
        return "redirect:/stores/" + storeId + "/store-review-manager";
    }

    /**
     * 사업자 : 본인이 작성한 답글 삭제.
     *
     * @param storeId the store id
     * @param replyId the reply id
     * @return the string
     */
    @DeleteMapping("/stores/{storeId}/store-review-manager/reply/{replyId}")
    public String deleteBusinessReviewReply(@PathVariable("storeId") Long storeId,
                                            @PathVariable("replyId") Long replyId) {
        reviewBusinessStoreService.removeAccountReviewByReviewReply(replyId);
        return "redirect:/stores/" + storeId + "/store-review-manager";
    }


    private void commonInfo(Model model, Long accountId) {

        List<AccountAddressResponseDto> accountAddresses =
            accountAddressService.selectAccountAddressAll(accountId);
        CartMenuCountDto cartMenuCountDto =
            cartService.selectCartMenuCountAll(String.valueOf(accountId));

        if (cartService.existMenuInCartRedis(String.valueOf(accountId), EMPTY_CART)) {
            model.addAttribute("count", CART_COUNT_ZERO);
        } else {
            model.addAttribute("count", cartMenuCountDto.getCount());
        }

        model.addAttribute("accountAddresses", accountAddresses);

        List<SelectAllStoresResponseDto> businessStoreList = storeService.selectStores(accountId);
        model.addAttribute("businessStoreList", businessStoreList);
    }
}
