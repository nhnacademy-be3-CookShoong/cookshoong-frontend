package store.cookshoong.www.cookshoongfrontend.auth.interceptor;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.PathMatcher;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.HandlerInterceptor;
import store.cookshoong.www.cookshoongfrontend.auth.authentication.AuthenticationHolder;
import store.cookshoong.www.cookshoongfrontend.auth.authentication.JwtAuthentication;
import store.cookshoong.www.cookshoongfrontend.auth.authentication.RedisAuthenticationHolder;
import store.cookshoong.www.cookshoongfrontend.auth.model.response.AuthenticationResponseDto;
import store.cookshoong.www.cookshoongfrontend.auth.service.TokenManagementService;
import store.cookshoong.www.cookshoongfrontend.auth.util.JwtResolver;
import store.cookshoong.www.cookshoongfrontend.common.util.ExceptionUtils;

/**
 * 토큰이 만료되기 전 미리 재발급 받기 위한 인터셉터.
 *
 * @author koesnam (추만석)
 * @since 2023.07.27
 */
@Slf4j
@RequiredArgsConstructor
public class TokenReissueInterceptor implements HandlerInterceptor {
    private final TokenManagementService tokenManagementService;
    private static final PathMatcher antMatcher = new AntPathMatcher();
    private static final String[] EXCLUDED_PATH = {"/images/**", "/logout", "/error"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws IOException, ServletException {
        Principal principal = request.getUserPrincipal();
        String uri = request.getRequestURI();

        if (Objects.isNull(principal) || shouldNotCheck(uri)) {
            return true;
        }

        String accessToken = principal.getName();
        if (canReissue(request, accessToken)) {
            reissueAndUpdatePrincipal(request, response);
        }
        return true;
    }

    private void reissueAndUpdatePrincipal(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Assert.isTrue(tokenManagementService.hasRefreshToken(request), "리프레쉬 토큰이 없는 상태로 재발급은 불가능합니다.");
        try {
            AuthenticationResponseDto authResponse = tokenManagementService.reissueToken();
            updateCurrentAccessToken(request, authResponse.getAccessToken());
            tokenManagementService.saveRefreshToken(response, authResponse.getRefreshToken());
            log.info("============ 토큰 재발급 성공 : {} =============== \naccessToken = {} \nrefreshToken = {}",
                JwtResolver.resolveRefreshToken(authResponse.getRefreshToken()).getLoginId(),
                authResponse.getAccessToken(), authResponse.getRefreshToken());
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
                log.warn("액세스 토큰과 리프레쉬 토큰 불일치로 인한 토큰 재발급 실패");
                log.warn("+액세스 토큰과 리프레쉬 토큰 불일치로 인한 강제 로그아웃+");
                return;
            }
            log.error("토큰 재발급 요청 실패 \n 상태코드 : {} refreshToken : {}", e.getRawStatusCode(),
                tokenManagementService.getRefreshToken());
        } catch (HttpServerErrorException e) {
            log.error("토큰 재발급 중 외부 서버에서 에러 발생 {} \n--> {}", e.getClass(), ExceptionUtils.getStackTrace(e));
        } catch (Exception e) {
            log.error("토큰 재발급 중 프론트 서버 내에서 에러 발생 {} \n--> {}", e.getClass(), ExceptionUtils.getStackTrace(e));
        }
    }

    private boolean canReissue(HttpServletRequest request, String accessToken) {
        return tokenManagementService.isTimeToLiveUnderFiveMinutes(accessToken)
            && tokenManagementService.hasRefreshToken(request);
    }

    private static boolean shouldNotCheck(String uri) {
        return Arrays.stream(EXCLUDED_PATH)
            .anyMatch(pattern -> antMatcher.match(pattern, uri));
    }

    /**
     * 회원이 가지고 있는 토큰을 재발급한 토큰으로 바꿔주는 메서드.
     *
     * @param accessToken the access token
     */
    public void updateCurrentAccessToken(HttpServletRequest request, String accessToken) {
        JwtAuthentication oldAuthentication = (JwtAuthentication) AuthenticationHolder.getCurrentUser();
        oldAuthentication.updateAccessToken(accessToken);
        RedisAuthenticationHolder.updateCurrentUser(request, oldAuthentication);
        log.info("========== 재발급후 액세스 토큰 : {} ================", AuthenticationHolder.getCurrentUser().getName());
    }
}
