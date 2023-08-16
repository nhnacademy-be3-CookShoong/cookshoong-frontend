package store.cookshoong.www.cookshoongfrontend.review.service;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import store.cookshoong.www.cookshoongfrontend.file.model.FileDomain;
import store.cookshoong.www.cookshoongfrontend.review.adapter.ReviewAdapter;
import store.cookshoong.www.cookshoongfrontend.review.exception.RegisterReviewFailureException;
import store.cookshoong.www.cookshoongfrontend.review.model.request.CreateReviewRequestDto;
import store.cookshoong.www.cookshoongfrontend.review.model.request.UpdateReviewResponseDto;
import store.cookshoong.www.cookshoongfrontend.review.model.response.SelectReviewResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.exception.RegisterStoreFailureException;

/**
 * 리뷰 등록, 수정, 조회 등의 로직 작성.
 *
 * @author seungyeon
 * @since 2023.08.13
 */
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewAdapter reviewAdapter;

    public void createReview(Long accountId,
                               CreateReviewRequestDto requestDto,
                               List<MultipartFile> reviewImages) {

        MultiValueMap<String, Object> fileMap = new LinkedMultiValueMap<>();
        fileMap.add("requestDto", requestDto);

        if (Objects.nonNull(reviewImages)) {
            for (MultipartFile file : reviewImages){
                fileMap.add(FileDomain.REVIEW.getVariable(), file.getResource());
            }
        }

        ResponseEntity<Void> response = reviewAdapter.executeCreateReview(accountId, fileMap);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RegisterReviewFailureException(response.getStatusCode());
        }
    }

    public void updateReview(Long accountId, Long reviewId, UpdateReviewResponseDto updateReviewResponseDto){
        ResponseEntity<Void> response = reviewAdapter.executeUpdateReview(accountId, reviewId, updateReviewResponseDto);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RegisterReviewFailureException(response.getStatusCode());
        }
    }

    /**
     * 회원이 작성한 리뷰에 대해 조회하는 메서드.
     *
     * @param accountId     회원 아이디
     * @param pageable      페이지 처리
     * @return              회원이 작성한 모든 리뷰를 반환
     */
    public Page<SelectReviewResponseDto> selectReviewByAccount(Long accountId, Pageable pageable) {

        return reviewAdapter.fetchReviewByAccount(accountId, pageable);
    }
}
