package store.cookshoong.www.cookshoongfrontend.review.service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import store.cookshoong.www.cookshoongfrontend.file.model.FileDomain;
import store.cookshoong.www.cookshoongfrontend.review.adapter.ReviewAdapter;
import store.cookshoong.www.cookshoongfrontend.review.model.request.CreateReviewRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.exception.RegisterStoreFailureException;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateStoreRequestDto;

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

    public String createReview(Long accountId, UUID orderCode,
                               CreateReviewRequestDto requestDto,
                               List<MultipartFile> reviewImages) {

        MultiValueMap<String, Object> fileMap = new LinkedMultiValueMap<>();
        fileMap.add("requestDto", requestDto);

        if (Objects.nonNull(reviewImages)) {
            for (MultipartFile file : reviewImages){
                fileMap.add(FileDomain.REVIEW.getVariable(), file.getResource());
            }
        }

        ResponseEntity<Void> response = reviewAdapter.executeCreateReview(accountId, orderCode, fileMap);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RegisterStoreFailureException(response.getStatusCode());
        }
        return Objects.requireNonNull(response.getHeaders().getLocation()).getPath();
    }
}
