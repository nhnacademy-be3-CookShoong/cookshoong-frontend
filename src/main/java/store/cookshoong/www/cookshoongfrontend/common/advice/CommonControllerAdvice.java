package store.cookshoong.www.cookshoongfrontend.common.advice;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;
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
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handle401Exception(Exception exception, Model model) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "/error/4xx";
    }

    /**
     * 권한이 없거나 낮은 권한 상위 권한으로의 접근이 일어날 때의 에러를 처리하는 메서드.
     *
     * @param exception the exception
     * @return the string
     */
    @ExceptionHandler({HttpClientErrorException.Forbidden.class, AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handle403Exception(Exception exception) {
        return "error/4xx";
    }

    /**
     * 요청하는 리소스가 없을 때의 에러를 처리하는 메서드.
     *
     * @param exception the exception
     * @return the string
     */
    @ExceptionHandler({NoHandlerFoundException.class, NotFoundException.class,
        HttpClientErrorException.NotFound.class, LocationTypeNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle404Exception(Exception exception) {
        return "error/4xx";
    }

    /**
     * 5xx 시리즈의 에러를 잡는 메서드.
     *
     * @param exception the exception
     * @return the string
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handle5xxException(Exception exception) {
        log.error("server exception caused by: {}", exception.getCause().toString());
        log.error("server exception message : {}", exception.getMessage());
        Arrays.stream(exception.getStackTrace()).forEach(
            trace -> log.error(trace.toString())
        );
        return "error/5xx";
    }
}
