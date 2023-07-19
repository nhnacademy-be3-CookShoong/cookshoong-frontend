package store.cookshoong.www.cookshoongfrontend.shop.exception;

import org.springframework.http.HttpStatus;

/**
 * 매장 상태에 수정에 대한 예외.
 *
 * @author seungyeon (유승연)
 * @since 2023.07.16
 */
public class UpdateStatusFailureException extends RuntimeException {
    public UpdateStatusFailureException(HttpStatus status) {
        super(status.name() + " : " + status.getReasonPhrase());
    }
}
