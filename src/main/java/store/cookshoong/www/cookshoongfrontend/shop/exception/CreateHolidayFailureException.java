package store.cookshoong.www.cookshoongfrontend.shop.exception;

import org.springframework.http.HttpStatus;

/**
 * API 서버에 휴업일 신규등록 요청 실패시 예외.
 *
 * @author papel (윤동현)
 * @since 2023.07.10
 */
public class CreateHolidayFailureException extends RuntimeException {
    public CreateHolidayFailureException(HttpStatus status) {
        super(status.name() + " : " + status.getReasonPhrase());
    }
}
