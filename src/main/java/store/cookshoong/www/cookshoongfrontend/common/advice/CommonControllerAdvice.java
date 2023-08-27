package store.cookshoong.www.cookshoongfrontend.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;
import store.cookshoong.www.cookshoongfrontend.auth.exception.RefreshTokenExpiredException;
import store.cookshoong.www.cookshoongfrontend.common.exception.NotFoundException;
import store.cookshoong.www.cookshoongfrontend.file.exception.LocationTypeNotFoundException;

/**
 * 애플리케이션 전역으로 컨트롤러 내부에서 터지는 예외를 처리하기위한 핸들러.
 *
 * @author koesnam (추만석)
 * @since 2023.08.14
 */
@Slf4j
@ControllerAdvice(basePackages = "store.cookshoong.www.cookshoongfrontend")
public class CommonControllerAdvice {
    /**
     * 인증되지 않는 사용자의 접근이 일어날 때의 에러를 처리하는 메서드.
     *
     * @param exception the exception
     * @param model     the model
     * @return the string
     */
    @ExceptionHandler({AuthenticationException.class, HttpClientErrorException.Unauthorized.class})
    public String handle401Exception(Exception exception, Model model) {
        model.addAttribute("status", 401);
        model.addAttribute("errorMessage", exception.getMessage());
        return "/error/4xx";
    }

    /**
     * 리프레쉬 토큰 만료시에 로그인 페이지로 보내기위한 메서드.
     *
     * @return the string
     */
    @ExceptionHandler(RefreshTokenExpiredException.class)
    public String handleRefreshTokenExpired() {
        return "redirect:/login-page";
    }

    /**
     * 권한이 없거나 낮은 권한 상위 권한으로의 접근이 일어날 때의 에러를 처리하는 메서드.
     *
     * @param exception the exception
     * @param model     the model
     * @return the string
     */
    @ExceptionHandler({HttpClientErrorException.Forbidden.class, AccessDeniedException.class})
    public String handle403Exception(Exception exception, Model model) {
        model.addAttribute("status", 403);
        model.addAttribute("errorMessage", exception.getMessage());
        return "error/4xx";
    }

    /**
     * 요청하는 리소스가 없을 때의 에러를 처리하는 메서드.
     *
     * @param exception the exception
     * @param model     the model
     * @return the string
     */
    @ExceptionHandler({NoHandlerFoundException.class, NotFoundException.class, HttpClientErrorException.NotFound.class,
        LocationTypeNotFoundException.class})
    public String handle404Exception(Exception exception, Model model) {
        model.addAttribute("status", 404);
        return "error/4xx";
    }

    /**
     * 5xx 시리즈의 에러를 잡는 메서드.
     *
     * @param exception the exception
     * @return the string
     */
    @ExceptionHandler(Exception.class)
    public String handle5xxException(Exception exception) {
        return "error/5xx";
    }
}
