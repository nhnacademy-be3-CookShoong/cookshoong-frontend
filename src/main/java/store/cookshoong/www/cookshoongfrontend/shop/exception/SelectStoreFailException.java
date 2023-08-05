package store.cookshoong.www.cookshoongfrontend.shop.exception;

import org.springframework.http.HttpStatus;

/**
 * 매장 조회에 대한 예외.
 *
 * @author seungyeon (유승연)
 * @since 2023.08.05
 */
public class SelectStoreFailException extends RuntimeException {
    public SelectStoreFailException(HttpStatus status) {
        super(status.name() + " : " + status.getReasonPhrase());
    }
}
