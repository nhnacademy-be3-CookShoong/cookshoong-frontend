package store.cookshoong.www.cookshoongfrontend.review.exception;

import org.springframework.http.HttpStatus;

/**
 * 리뷰 등록/수정 시 validation 예외.
 *
 * @author seungyeon
 * @since 2023.08.16
 */
public class RegisterReviewFailureException extends RuntimeException {
    public RegisterReviewFailureException(HttpStatus status) {
        super(status.name() + " : " + status.getReasonPhrase());
    }
}
