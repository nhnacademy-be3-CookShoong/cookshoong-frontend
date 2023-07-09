package store.cookshoong.www.cookshoongfrontend.store.exception;

import org.springframework.http.HttpStatus;

/**
 * API 서버에 매장 신규등록 요청 실패시 예외.
 *
 * @author papel
 * @since 2023.07.09
 */
public class CreateStoreFailureException extends RuntimeException {
    public CreateStoreFailureException(HttpStatus status) {
        super(status.name() + " : " + status.getReasonPhrase());
    }
}
