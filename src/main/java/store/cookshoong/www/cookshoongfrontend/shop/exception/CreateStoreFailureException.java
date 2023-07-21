package store.cookshoong.www.cookshoongfrontend.shop.exception;

import org.springframework.http.HttpStatus;

/**
 * API 서버에 매장 신규등록 요청 실패 예외.
 *
 * @author papel (윤동현)
 * @since 2023.07.09
 */
public class CreateStoreFailureException extends RuntimeException {
    public CreateStoreFailureException(HttpStatus status) {
        super(status.name() + " : " + status.getReasonPhrase());
    }
}
