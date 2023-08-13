package store.cookshoong.www.cookshoongfrontend.review.controller;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.review.model.request.CreateReviewRequestDto;
import store.cookshoong.www.cookshoongfrontend.review.service.ReviewService;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateStoreRequestDto;

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
    // TODO 현재 경로 /review?orderCode=** 경로 확인해주세요. 마음에 안드시면 바꿔서 해주세요.
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
