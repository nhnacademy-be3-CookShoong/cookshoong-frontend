package store.cookshoong.www.cookshoongfrontend.auth.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DelegatingOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;
import store.cookshoong.www.cookshoongfrontend.auth.filter.DormancyAccountFilter;
import store.cookshoong.www.cookshoongfrontend.auth.hanlder.ForbiddenAccessHandler;
import store.cookshoong.www.cookshoongfrontend.auth.hanlder.LoginFailureHandler;
import store.cookshoong.www.cookshoongfrontend.auth.hanlder.LoginSuccessHandler;
import store.cookshoong.www.cookshoongfrontend.auth.hanlder.OAuth2AccountNotFoundHandler;
import store.cookshoong.www.cookshoongfrontend.auth.hanlder.OAuth2LoginSuccessHandler;
import store.cookshoong.www.cookshoongfrontend.auth.hanlder.TokenInvalidationHandler;
import store.cookshoong.www.cookshoongfrontend.auth.repository.RefreshTokenManager;

/**
 * 인증과 권한 기반으로 요청들을 검증을 하기 위한 시큐리티 설정 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.13
 */
@SuppressWarnings("checkstyle:MemberName")
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2AccountNotFoundHandler oAuth2AccountNotFoundHandler;
    private final TokenInvalidationHandler tokenInvalidationHandler;
    private final List<OAuth2UserService<OAuth2UserRequest, OAuth2User>> oAuth2UserServiceList;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final OAuth2AuthorizationRequestResolver authorizationRequestResolver;
    private final ForbiddenAccessHandler forbiddenAccessHandler;
    private final DormancyAccountFilter dormancyAccountFilter = new DormancyAccountFilter();
    private static final String[] PERMIT_ALL_PATTERNS = {"/health-check/**", "/login-page", "/sign-up",
        "/sign-up-choice", "/", "/search/page/distance", "/config", "/fragments", "/fragments-admin", "/images/**",
        "/sign-up-oauth2", "/logout", "/proxy/**", "/error*/**", "/delivery", "/swagger/**", "/swagger-ui/**",
        "/management/logger", "/actuator/loggers/**"};

    /**
     * 시큐리티 필터 체인 설정빈.
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
            .mvcMatchers(PERMIT_ALL_PATTERNS).permitAll()
            .mvcMatchers("/dormancy/**").hasAnyRole("DORMANCY")
            .mvcMatchers("/stores/**").hasRole("BUSINESS")
            .mvcMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().hasAnyRole("ADMIN", "BUSINESS", "CUSTOMER");

        http.formLogin()
            .loginPage("/login-page")
            .usernameParameter("loginId")
            .passwordParameter("password")
            .loginProcessingUrl("/login")
            .successHandler(loginSuccessHandler)
            .failureHandler(loginFailureHandler);

        http.oauth2Login()
            .loginPage("/login-page")
            .clientRegistrationRepository(clientRegistrationRepository)
            .authorizedClientService(authorizedClientService)
            .authorizationEndpoint(c -> c.authorizationRequestResolver(authorizationRequestResolver))
            .userInfoEndpoint(c -> c.userService(new DelegatingOAuth2UserService<>(oAuth2UserServiceList)))
            .successHandler(oAuth2LoginSuccessHandler)
            .failureHandler(oAuth2AccountNotFoundHandler);

        http.logout()
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .deleteCookies("SESSION", RefreshTokenManager.REFRESH_TOKEN_COOKIE_NAME)
            .logoutSuccessHandler(tokenInvalidationHandler);

        http.sessionManagement()
            .sessionFixation()
            .newSession();

        http.addFilterAfter(dormancyAccountFilter, SwitchUserFilter.class);

        http.csrf()
            .ignoringAntMatchers("/delivery", "/actuator/loggers/**");

        http.exceptionHandling()
            .accessDeniedHandler(forbiddenAccessHandler);

        http.httpBasic()
            .disable();
        return http.build();
    }

    /**
     * Security 필터에서 제외될 경로를 설정한다.
     *
     * @return the web security customizer
     */
    @Bean
    public WebSecurityCustomizer webSecurity() {
        return web -> web.ignoring().mvcMatchers("/assets/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    static RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_BUSINESS > ROLE_CUSTOMER > ROLE_DORMANCY");
        return roleHierarchy;
    }

    @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }
}
