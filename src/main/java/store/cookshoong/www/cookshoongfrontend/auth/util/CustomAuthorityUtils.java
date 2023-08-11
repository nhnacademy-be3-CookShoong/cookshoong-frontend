package store.cookshoong.www.cookshoongfrontend.auth.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * 기존 AuthorityUtils 에서 원하는 기능을 더하기 위해 만든 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.08.06
 */
public class CustomAuthorityUtils {
    private CustomAuthorityUtils() {
    }

    public static Set<GrantedAuthority> createAuthoritySet(String... authorities) {
        return new HashSet<>(AuthorityUtils.createAuthorityList(authorities));
    }

    public static boolean match(String target, Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
            .anyMatch(auth -> auth.getAuthority().equals(target));
    }
}
