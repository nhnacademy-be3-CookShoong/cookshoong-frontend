package store.cookshoong.www.cookshoongfrontend.review.controller;

import java.util.List;
import javax.validation.Valid;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountService;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AccountAddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.service.AccountAddressService;
import store.cookshoong.www.cookshoongfrontend.review.model.request.CreateReviewRequestDto;
import store.cookshoong.www.cookshoongfrontend.review.model.response.SelectReviewResponseDto;
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
     * @param model                  the model
     * @param createReviewRequestDto the create review request dto
     * @return the my review
     */
    @GetMapping("/my-review")
    public String getMyReview(Model model, @PageableDefault Pageable pageable,
                              CreateReviewRequestDto createReviewRequestDto) {

        List<AccountAddressResponseDto> accountAddresses =
            accountAddressService.selectAccountAddressAll(accountIdAware.getAccountId());
        Page<SelectReviewResponseDto> accountReviews =
            reviewService.selectReviewByAccount(accountIdAware.getAccountId(), pageable);

        model.addAttribute("accountReviews", accountReviews);
        model.addAttribute("createReviewRequestDto", createReviewRequestDto);
        model.addAttribute("accountAddresses", accountAddresses);
        model.addAttribute("accountInfo", accountService.selectAccountMypageInfo(accountIdAware.getAccountId()));
        return "account/my-review";
    }

    /**
     * Post create store string.
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

        if (bindingResult.hasErrors()){
            throw new ValidationException();
        }

        Long accountId = accountIdAware.getAccountId();
        reviewService.createReview(accountId, createReviewRequestDto, reviewImages);
        return "redirect:/my-review";
    }
}
