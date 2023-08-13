package store.cookshoong.www.cookshoongfrontend.auth.interceptor;

import java.io.IOException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * RestTemplate 을 통해 API 를 호출하기전 인증 헤더를 달아주기 위한 인터셉터.
 *
 * @author koesnam (추만석)
 * @since 2023.07.24
 */
public class RestTemplateHeaderModifierInterceptor implements ClientHttpRequestInterceptor {
    /**
     * RestTemplate 을 통해 API 를 호출하기 전 인증 헤더를 추가해준다.
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
        throws IOException {
        SecurityContext context = SecurityContextHolder.getContext();
        if (isNotAuthenticated(context)) {
            return execution.execute(request, body);
        }

        String accessToken = context.getAuthentication().getName();
        request.getHeaders().setBearerAuth(accessToken);
        return execution.execute(request, body);
    }

    private static boolean isNotAuthenticated(SecurityContext context) {
        return context == null || context.getAuthentication() == null;
    }
}
