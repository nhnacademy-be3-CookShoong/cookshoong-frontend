package store.cookshoong.www.cookshoongfrontend.auth.repository;

import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import store.cookshoong.www.cookshoongfrontend.auth.entity.RefreshToken;


/**
 * Refresh 토큰을 쿠키로부터 가져오기위한 Repository.
 *
 * @author koesnam (추만석)
 * @since 2023.08.11
 */
@Component
public class RefreshTokenManager implements CookieManager {
    public static final String REFRESH_TOKEN_COOKIE_NAME = "CRT";

    /**
     * 리프레쉬 토큰을 쿠키에 저장한다.
     *
     * @param response     the response
     * @param refreshToken the refresh token
     */
    public void save(HttpServletResponse response, RefreshToken refreshToken) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, refreshToken.getRawRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(System.getProperty("spring.profiles.active").equals("prod"));
        cookie.setPath("/");
        cookie.setMaxAge(Math.toIntExact(refreshToken.getExpireTime()));
        CookieManager.super.save(response, cookie);
        ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, refreshToken.getRawRefreshToken())
            .build();
    }

    /**
     * 리프레쉬 토큰을 쿠키에서 가져온다.
     * CRT(Cookshoong Refresh Token)
     *
     * @param request the request
     * @return the optional
     */
    public RefreshToken extractRefreshToken(HttpServletRequest request) {
        Optional<Cookie> cookie = extractCookieByName(request, REFRESH_TOKEN_COOKIE_NAME);
        if (cookie.isEmpty()) {
            return null;
        }
        String rawRefreshToken = cookie.get().getValue();
        return RefreshToken.createRefreshToken(rawRefreshToken);
    }

    /**
     * 리프레쉬 토큰을 가장 최근 요청에서의 쿠키를 가져온다.
     * CRT(Cookshoong Refresh Token)
     *
     * @return the optional
     */
    public RefreshToken getCurrentRefreshToken() {
        return extractRefreshToken(CookieManager.getRequest());
    }

    public boolean isExists(HttpServletRequest request) {
        return this.extractRefreshToken(request) != null;
    }

    public void delete(HttpServletResponse response) {
        CookieManager.super.delete(response, REFRESH_TOKEN_COOKIE_NAME);
    }
}
