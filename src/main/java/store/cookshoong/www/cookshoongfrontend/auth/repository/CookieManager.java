package store.cookshoong.www.cookshoongfrontend.auth.repository;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

/**
 * Cookie 에서 값을 가져오기 위한 레포지토리.
 *
 * @author koesnam (추만석)
 * @since 2023.08.11
 */
public interface CookieManager {

    /**
     * 쿠키명을 기준으로 쿠키를 가져온다.
     *
     * @param request the request
     * @param name    the name
     * @return the optional
     */
    default Optional<Cookie> extractCookieByName(HttpServletRequest request, String name) {
        return Arrays.stream(request.getCookies())
            .filter(cookie -> cookie.getName().equals(name))
            .findAny();
    }

    default void save(HttpServletResponse response, Cookie cookie) {
        response.addCookie(cookie);
    }

    /**
     * 쿠키명을 기준으로 쿠키를 삭제한다.
     *
     * @param response the response
     * @param name     the name
     */
    default void delete(HttpServletResponse response, String name) {
        Cookie cookie = (new Cookie(name, null));
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }
}
