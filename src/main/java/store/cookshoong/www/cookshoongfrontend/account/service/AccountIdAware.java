package store.cookshoong.www.cookshoongfrontend.account.service;

import org.springframework.security.authentication.InsufficientAuthenticationException;

/**
 * AccountId의 반환을 약속하는 계약.
 *
 * @author koesnam (추만석)
 * @since 2023.07.18
 */
public interface AccountIdAware {
    Long getAccountId() throws InsufficientAuthenticationException;
}
