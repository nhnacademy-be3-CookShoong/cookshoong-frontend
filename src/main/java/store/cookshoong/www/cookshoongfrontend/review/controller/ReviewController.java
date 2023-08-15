package store.cookshoong.www.cookshoongfrontend.review.controller;

import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountService;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AccountAddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.service.AccountAddressService;
import store.cookshoong.www.cookshoongfrontend.review.model.request.CreateReviewRequestDto;
import store.cookshoong.www.cookshoongfrontend.review.service.ReviewService;

/**
 * 리뷰 등록, 수정, 조회를 위한 controller.
 *
 * @author seungyeon
 * @since 2023.08.13
 */
@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final AccountIdAware accountIdAware;
    private final AccountAddressService accountAddressService;
    private final AccountService accountService;

    /**
     * 회원 리뷰 페이지 진입.
     *
     */
    @GetMapping("/my-review")
    public String getMyReview(Model model) {
        List<AccountAddressResponseDto> accountAddresses =
            accountAddressService.selectAccountAddressAll(accountIdAware.getAccountId());

        model.addAttribute("accountAddresses", accountAddresses);
        model.addAttribute("accountInfo", accountService.selectAccountMypageInfo(accountIdAware.getAccountId()));
        return "account/my-review";
    }

    @PostMapping("/review")
    public String postCreateStore(
        @RequestParam("orderCode") UUID orderCode,
        @Valid @ModelAttribute("createReviewRequestDto") CreateReviewRequestDto createReviewRequestDto,
        BindingResult bindingResult, Model model,
        @RequestPart("reviewImage") List<MultipartFile> reviewImages) {

        //TODO binding 처리

        Long accountId = accountIdAware.getAccountId();
        reviewService.createReview(accountId, orderCode, createReviewRequestDto, reviewImages);
        return null; //TODO result 값 변경
    }
}