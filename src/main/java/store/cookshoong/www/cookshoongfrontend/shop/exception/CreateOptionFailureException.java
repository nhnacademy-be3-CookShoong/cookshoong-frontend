package store.cookshoong.www.cookshoongfrontend.shop.exception;

import org.springframework.http.HttpStatus;

/**
 * API 서버에 옵션 신규등록 요청 실패 예외.
 *
 * @author papel
 * @since 2023.07.18
 */
public class CreateOptionFailureException extends RuntimeException {
    public CreateOptionFailureException(HttpStatus status) {
        super(status.name() + " : " + status.getReasonPhrase());
    }
}
