package store.cookshoong.www.cookshoongfrontend.store.exception;

import org.springframework.http.HttpStatus;

/**
 * API 서버에 영업시간 신규등록 요청 실패시 예외.
 *
 * @author papel
 * @since 2023.07.10
 */
public class CreateBusinessHourFailureException extends RuntimeException {
    public CreateBusinessHourFailureException(HttpStatus status) {
        super(status.name() + " : " + status.getReasonPhrase());
    }
}
