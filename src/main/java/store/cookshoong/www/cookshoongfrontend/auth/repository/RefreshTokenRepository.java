package store.cookshoong.www.cookshoongfrontend.auth.repository;

import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import store.cookshoong.www.cookshoongfrontend.auth.entity.RefreshToken;


/**
 * Refresh 토큰을 쿠키로부터 가져오기위한 Repository.
 *
 * @author koesnam (추만석)
 * @since 2023.08.11
 */
@Component
public class RefreshTokenRepository implements CookieRepository {
    private static final String REFRESH_TOKEN_COOKIE_NAME = "CRT";

    /**
     * 리프레쉬 토큰을 쿠키에 저장한다.
     *
     * @param response     the response
     * @param refreshToken the refresh token
     */
    public void save(HttpServletResponse response, RefreshToken refreshToken) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, refreshToken.getRawRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(Math.toIntExact(refreshToken.getExpireTime()));
        CookieRepository.super.save(response, cookie);
    }

    /**
     * 리프레쉬 토큰을 쿠키에서 가져온다.
     * CRT(Cookshoong Refresh Token)
     *
     * @param request the request
     * @return the optional
     */
    public Optional<Cookie> findByName(HttpServletRequest request) {
        return CookieRepository.super.findByName(request, REFRESH_TOKEN_COOKIE_NAME);
    }

    public boolean isExists(HttpServletRequest request) {
        return this.findByName(request).isPresent();
    }

    public void delete(HttpServletResponse response) {
        CookieRepository.super.delete(response, REFRESH_TOKEN_COOKIE_NAME);
    }
}
