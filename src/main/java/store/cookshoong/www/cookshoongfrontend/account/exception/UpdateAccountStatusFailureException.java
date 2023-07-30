package store.cookshoong.www.cookshoongfrontend.account.exception;

/**
 * 회원 상태 변경 실패시 일어나는 예외.
 *
 * @author koesnam (추만석)
 * @since 2023.07.29
 */
public class UpdateAccountStatusFailureException extends RuntimeException {
    public UpdateAccountStatusFailureException() {
        super("회원 상태 업데이트 실패");
    }
}
