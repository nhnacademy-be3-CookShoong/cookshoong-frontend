package store.cookshoong.www.cookshoongfrontend.account.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * accountId를 할당해주기 위해 만든 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.13
 */
@Deprecated(forRemoval = true, since = "AccountIdAware 을 주입받아서 사용해주세요.")
@Getter
@Setter
@AllArgsConstructor
public class DevAccountIdOnly {
    private Long accountId;
}
