package store.cookshoong.www.cookshoongfrontend.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.review.adapter.ReviewBusinessAdapter;
import store.cookshoong.www.cookshoongfrontend.review.model.request.CreateBusinessReviewRequestDto;
import store.cookshoong.www.cookshoongfrontend.review.model.request.ModifyBusinessReviewRequestDto;
import store.cookshoong.www.cookshoongfrontend.review.model.request.UpdateReplyResponseDto;
import store.cookshoong.www.cookshoongfrontend.review.model.response.SelectReviewResponseDto;
import store.cookshoong.www.cookshoongfrontend.review.model.response.SelectReviewStoreResponseDto;

/**
 * 매장에 대한 회원 리뷰 조회 등의 로직 작성.
 *
 * @author seungyeon
 * @since 2023.08.13
 */
@Service
@RequiredArgsConstructor
public class ReviewBusinessStoreService {

    private final ReviewBusinessAdapter reviewBusinessAdapter;

    /**
     * 사장님이 회원 리뷰에 대해 답글을 작성하는 메서드.
     *
     * @param reviewId      리뷰 아이디
     * @param requestDto    리뷰 답글 내용
     */
    public void createAccountReviewByReviewReply(Long reviewId, CreateBusinessReviewRequestDto requestDto) {

        reviewBusinessAdapter.executeAccountReviewByReviewReply(reviewId, requestDto);
    }

    /**
     * 사장님이 회원 리뷰에 대해 답글을 수정하는 메서드.
     *
     * @param reviewReplyId      리뷰 답글 아이디
     * @param requestDto         리뷰 답글 내용
     */
    public void updateAccountReviewByReviewReply(Long reviewReplyId, UpdateReplyResponseDto requestDto) {

        reviewBusinessAdapter.changeAccountReviewByReviewReply(reviewReplyId, requestDto);
    }

    /**
     * 사장님 매장에 대해 회원이 작성한 리뷰에 대해 조회하는 메서드.
     *
     * @param storeId       매장 아이디
     * @param pageable      페이지 처리
     * @return              회원이 작성한 모든 리뷰를 반환
     */
    public Page<SelectReviewStoreResponseDto> selectReviewByStore(Long storeId, Pageable pageable) {

        return reviewBusinessAdapter.fetchBusinessReviewByStore(storeId, pageable);
    }

    /**
     * 사장님이 회원 리뷰에 대해 답글을 삭제하는 메서드.
     *
     * @param reviewReplyId      리뷰 답글 아이디
     */
    public void removeAccountReviewByReviewReply(Long reviewReplyId) {

        reviewBusinessAdapter.eraseAccountReviewByReviewReply(reviewReplyId);
    }
}
