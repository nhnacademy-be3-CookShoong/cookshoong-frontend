package store.cookshoong.www.cookshoongfrontend.auth.repository;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;
import store.cookshoong.www.cookshoongfrontend.auth.exception.RefreshTokenExpiredException;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.RefreshToken;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


/**
 * Refresh 토큰을 쿠키로부터 가져오기위한 Repository.
 *
 * @author koesnam (추만석)
 * @since 2023.08.11
 */
@Component
@RequiredArgsConstructor
public class RefreshTokenManager implements CookieManager {
    public static final String REFRESH_TOKEN_COOKIE_NAME = "CRT";
    private final TextEncryptor encryptor;

    /**
     * 리프레쉬 토큰을 쿠키에 저장한다.
     *
     * @param response     the response
     * @param refreshToken the refresh token
     */
    public void save(HttpServletResponse response, RefreshToken refreshToken) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, encryptor.encrypt(refreshToken.getRawRefreshToken()));
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(Math.toIntExact(refreshToken.getTimeToLive()));
        CookieManager.super.save(response, cookie);
    }

    /**
     * 리프레쉬 토큰을 유효기간이 있는 객체로 변환한다.
     * CRT(Cookshoong Refresh Token)
     *
     * @param request the request
     * @return the optional
     */
    public RefreshToken convertRefreshToken(HttpServletRequest request) {
        String rawRefreshToken = extractRefreshToken(request);
        if (Objects.isNull(rawRefreshToken)) {
            throw new RefreshTokenExpiredException();
        }
        return RefreshToken.createRefreshToken(rawRefreshToken);
    }

    /**
     * 리프레쉬 토큰을 쿠키에서 가져온다.
     * CRT(Cookshoong Refresh Token)
     *
     * @param request the request
     * @return the optional
     */
    public String extractRefreshToken(HttpServletRequest request) {
        Optional<Cookie> cookie = extractCookieByName(request, REFRESH_TOKEN_COOKIE_NAME);
        return cookie.map(value -> encryptor.decrypt(value.getValue()))
            .orElse(null);
    }

    /**
     * 리프레쉬 토큰을 가장 최근 요청에서의 쿠키를 가져온다.
     * CRT(Cookshoong Refresh Token)
     *
     * @return the optional
     */
    public RefreshToken getCurrentRefreshToken() {
        return convertRefreshToken(CookieManager.getRequest());
    }

    public boolean isExists(HttpServletRequest request) {
        return this.extractRefreshToken(request) != null;
    }

    public void delete(HttpServletResponse response) {
        CookieManager.super.delete(response, REFRESH_TOKEN_COOKIE_NAME);
    }
}
