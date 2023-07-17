package store.cookshoong.www.cookshoongfrontend.util;

import java.util.Arrays;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {설명을 작성해주세요}.
 *
 * @author jeongjewan
 * @since 2023.07.16
 */
public class CookieUtils {

    private CookieUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 주어진 쿠키 이름과 일치하는 쿠키를 HttpServletRequest 에서 가져옵니다.
     *
     * @param request   HttpServletRequest 객체
     * @param name      쿠키 이름
     * @return          일치하는 쿠키, 존재하지 않을 경우 null 반환
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {

        return Optional.ofNullable(request.getCookies())
            .flatMap(cookies -> Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(name))
                .findFirst())
            .orElse(null);
    }

    /**
     * 쿠키가 존재하는지 확인하고, 쿠키 값을 가져옵니다.
     *
     * @param request   HttpServletRequest 객체
     * @param name      쿠키 이름
     * @return          쿠키가 존재하면 쿠키 값을 반환하고, 쿠키가 존재하지 않으면 null 을 반환합니다.
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = CookieUtils.getCookie(request, name);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    /**
     * 주어진 쿠키 이름과 값으로 HttpServletResponse 에 쿠키를 추가합니다.
     * 쿠키의 유효 기간은 1일로 설정되며, 경로는 루트 경로로 설정됩니다.
     * 자바 스크립트에서 쿠키값을 읽어가지 못하도록 설정.
     * SSL 통신채널 연결 시에만 쿠키를 전송하도록 설정.
     *
     * @param response  HttpServletResponse 객체
     * @param name      쿠기 이름
     * @param value     쿠키 값
     */
    public static void addCookie(HttpServletResponse response, String name, String value) {

        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(86400);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}
