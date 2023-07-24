package store.cookshoong.www.cookshoongfrontend.auth.interceptor;

import java.io.IOException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.JwtAuthentication;

/**
 * RestTemplate 을 통해 API 를 호출하기전 인증 헤더를 달아주기 위한 인터셉터.
 *
 * @author koesnam (추만석)
 * @since 2023.07.24
 */
public class RestTemplateHeaderModifierInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
        throws IOException {
        SecurityContext session = SecurityContextHolder.getContext();
        if (isAuthenticated(session)) {
            return execution.execute(request, body);
        }

        String accessToken = session.getAuthentication().getName();
        request.getHeaders().setBearerAuth(accessToken);
        return execution.execute(request, body);
    }

    private static boolean isAuthenticated(SecurityContext session) {
        return session == null || !(session.getAuthentication() instanceof JwtAuthentication);
    }
}
