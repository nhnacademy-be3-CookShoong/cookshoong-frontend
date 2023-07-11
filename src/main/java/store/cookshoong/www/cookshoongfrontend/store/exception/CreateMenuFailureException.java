package store.cookshoong.www.cookshoongfrontend.store.exception;

import org.springframework.http.HttpStatus;

/**
 * API 서버에 메뉴 신규등록 요청 실패시 예외.
 *
 * @author papel
 * @since 2023.07.11
 */
public class CreateMenuFailureException extends RuntimeException {
    public CreateMenuFailureException(HttpStatus status) {
        super(status.name() + " : " + status.getReasonPhrase());
    }
}
