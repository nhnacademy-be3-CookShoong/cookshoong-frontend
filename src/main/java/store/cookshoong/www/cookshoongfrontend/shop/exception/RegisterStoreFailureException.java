package store.cookshoong.www.cookshoongfrontend.shop.exception;

import org.springframework.http.HttpStatus;

/**
 * 매장 등록에 대한 예외.
 *
 * @author seungyeon (유승연)
 * @since 2023.08.05
 */
public class RegisterStoreFailureException extends RuntimeException {
    public RegisterStoreFailureException(HttpStatus status) {
        super(status.name() + " : " + status.getReasonPhrase());
    }
}

