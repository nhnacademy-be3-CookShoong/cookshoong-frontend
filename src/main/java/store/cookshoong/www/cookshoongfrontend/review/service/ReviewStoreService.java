package store.cookshoong.www.cookshoongfrontend.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.review.adapter.ReviewStoreAdapter;
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
public class ReviewStoreService {
    private final ReviewStoreAdapter reviewStoreAdapter;

    /**
     * 매장에 대한 회원이 작성한 리뷰에 대해 조회하는 메서드.
     *
     * @param storeId       매장 아이디
     * @param pageable      페이지 처리
     * @return              회원이 작성한 모든 리뷰를 반환
     */
    public Page<SelectReviewStoreResponseDto> selectReviewByAccount(Long storeId, Pageable pageable) {

        return reviewStoreAdapter.fetchReviewByStore(storeId, pageable);
    }
}
