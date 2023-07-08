package store.cookshoong.www.cookshoongfrontend.account.exception;

import org.springframework.http.HttpStatus;

/**
 * API 서버에 회원가입 요청 실패시 예외.
 *
 * @author koesnam
 * @since 2023.07.08
 */
public class RegisterFailureException extends RuntimeException {
    public RegisterFailureException(HttpStatus status) {
        super(status.name() + " : " + status.getReasonPhrase());
    }
}
