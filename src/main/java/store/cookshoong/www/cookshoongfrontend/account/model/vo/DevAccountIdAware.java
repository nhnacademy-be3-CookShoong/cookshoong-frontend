package store.cookshoong.www.cookshoongfrontend.account.model.vo;

import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * AccountId가 필요한 작업에 추가해주는 임시 인터페이스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.13
 */
@Deprecated(forRemoval = true, since = "AccountIdAware 을 주입받아서 사용해주세요.")
public interface DevAccountIdAware {

    /**
     * 여러 컨트롤러에서 AccountId를 얻어오기 위한 메서드.
     *
     * @return the account id only
     */
    @ModelAttribute
    default DevAccountIdOnly getAccountIdOnly() {
        return new DevAccountIdOnly(1L);
    }
}
