package store.cookshoong.www.cookshoongfrontend.account.model.vo;

import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * AccountId가 필요한 작업에 추가해주는 인터페이스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.13
 */
public interface AccountIdAware {
    @ModelAttribute
    default AccountIdOnly getAccountIdOnly() {
        // TODO : SecruityContext에서 값 가져오기.
        // TODO : 복호화해서 반환하기.
        return new AccountIdOnly(1L);
    }
}
