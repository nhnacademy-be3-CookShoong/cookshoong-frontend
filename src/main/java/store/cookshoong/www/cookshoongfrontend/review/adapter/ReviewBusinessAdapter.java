package store.cookshoong.www.cookshoongfrontend.review.adapter;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.cookshoong.www.cookshoongfrontend.common.property.ApiProperties;
import store.cookshoong.www.cookshoongfrontend.common.util.RestResponsePage;
import store.cookshoong.www.cookshoongfrontend.review.model.request.CreateBusinessReviewRequestDto;
import store.cookshoong.www.cookshoongfrontend.review.model.request.ModifyBusinessReviewRequestDto;
import store.cookshoong.www.cookshoongfrontend.review.model.request.UpdateReplyResponseDto;
import store.cookshoong.www.cookshoongfrontend.review.model.response.SelectReviewResponseDto;
import store.cookshoong.www.cookshoongfrontend.review.model.response.SelectReviewStoreResponseDto;

/**
 * 사장님이 회원에 대한 리뷰 조회 및 답글 생성, 수정, 삭제 adapter.
 *
 * @author seungyeon
 * @since 2023.08.13
 */
@RequiredArgsConstructor
@Component
public class ReviewBusinessAdapter {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    /**
     * 사장님이 회원 리뷰에 대해 답글을 다는 Adapter.
     *
     * @param reviewId      리뷰 아이디
     * @param requestDto    리뷰 생성 내용
     */
    public void executeAccountReviewByReviewReply(Long reviewId, CreateBusinessReviewRequestDto requestDto) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("business")
            .pathSegment("review")
            .pathSegment("{reviewId}")
            .pathSegment("review-reply")
            .buildAndExpand(reviewId)
            .toUri();

        HttpEntity<CreateBusinessReviewRequestDto> httpEntity =
            new HttpEntity<>(requestDto);

        restTemplate.exchange(uri, POST, httpEntity, new ParameterizedTypeReference<>() {});
    }

    /**
     * 사장님이 회원 리뷰에 대해 답글을 다는 Adapter.
     *
     * @param reviewReplyId      리뷰 답글 아이디
     * @param requestDto         리뷰 생성 내용
     */
    public void changeAccountReviewByReviewReply(Long reviewReplyId, UpdateReplyResponseDto requestDto) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("business")
            .pathSegment("review")
            .pathSegment("review-reply")
            .pathSegment("{reviewReplyId}")
            .buildAndExpand(reviewReplyId)
            .toUri();

        HttpEntity<UpdateReplyResponseDto> httpEntity =
            new HttpEntity<>(requestDto);

        restTemplate.exchange(uri, PATCH, httpEntity, new ParameterizedTypeReference<>() {});
    }

    /**
     * 사장님이 회원이 작성 리뷰에 대해 조회하는 Adapter.
     *
     * @param storeId       매장 아이디
     * @param pageable      페이지 처리
     * @return              회원이 작성한 모든 리뷰를 반환
     */
    public RestResponsePage<SelectReviewStoreResponseDto> fetchBusinessReviewByStore(Long storeId, Pageable pageable) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("business")
            .pathSegment("review")
            .pathSegment("{storeId}")
            .queryParam("page", pageable.getPageNumber())
            .queryParam("size", pageable.getPageSize())
            .buildAndExpand(storeId)
            .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
            .accept(APPLICATION_JSON)
            .build();

        ResponseEntity<RestResponsePage<SelectReviewStoreResponseDto>> response =
            restTemplate.exchange(request, new ParameterizedTypeReference<>() {
            });

        return response.getBody();
    }

    /**
     * 사장님이 회원 리뷰에 대해 답글을 삭제하는 Adapter.
     *
     * @param reviewReplyId      리뷰 답글 아이디
     */
    public void eraseAccountReviewByReviewReply(Long reviewReplyId) {

        URI uri = UriComponentsBuilder
            .fromUriString(apiProperties.getGatewayUrl())
            .pathSegment("api")
            .pathSegment("business")
            .pathSegment("review")
            .pathSegment("review-reply")
            .pathSegment("{reviewReplyId}")
            .buildAndExpand(reviewReplyId)
            .toUri();

        restTemplate.exchange(uri, DELETE, null, new ParameterizedTypeReference<>() {});
    }
}
