package store.cookshoong.www.cookshoongfrontend.shop.exception;

import org.springframework.http.HttpStatus;

/**
 * 카테고리 조회에 대한 예외.
 *
 * @author seungyeon (유승연)
 * @since 2023.08.05
 */
public class SelectCategoriesFailException extends RuntimeException{
    public SelectCategoriesFailException(HttpStatus status){
        super(status.name() + " : " + status.getReasonPhrase());
    }
}
