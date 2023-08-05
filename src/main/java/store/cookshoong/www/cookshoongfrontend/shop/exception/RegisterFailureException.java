package store.cookshoong.www.cookshoongfrontend.shop.exception;

import org.springframework.http.HttpStatus;

/**
 * 등록폼에 대한 Fail 예외처리.
 *
 * @author seungyeon (유승연)
 * @since 2023.08.05
 */
public class RegisterFailureException extends RuntimeException{
    public RegisterFailureException(HttpStatus status) {
        super(status.name() + " : " + status.getReasonPhrase());
    }
}
