package store.cookshoong.www.cookshoongfrontend.auth.exception;

/**
 * 토큰 구문 분석 실패시 발생하는 예외.
 *
 * @author koesnam (추만석)
 * @since 2023.07.16
 */
public class InvalidAccessTokenException extends RuntimeException {
    public InvalidAccessTokenException() {
        super("토큰의 형식을 확인해주세요.");
    }
}
