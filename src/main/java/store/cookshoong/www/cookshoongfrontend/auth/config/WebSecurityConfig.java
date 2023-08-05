package store.cookshoong.www.cookshoongfrontend.auth.config;

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
import org.springframework.security.web.SecurityFilterChain;
import store.cookshoong.www.cookshoongfrontend.auth.hanlder.LoginSuccessHandler;
import store.cookshoong.www.cookshoongfrontend.auth.hanlder.TokenInvalidationHandler;
import store.cookshoong.www.cookshoongfrontend.auth.provider.JwtAuthenticationProvider;

/**
 * 인증과 권한 기반으로 요청들을 검증을 하기 위한 시큐리티 설정 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.13
 */
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final LoginSuccessHandler loginSuccessHandler;
    private final TokenInvalidationHandler tokenInvalidationHandler;
    private static final String[] PERMIT_ALL_PATTERNS = { "/health-check/**", "/login-page", "/sign-up",
        "/sign-up-choice", "/", "/config", "/fragments", "/fragments-admin", "/images/**"};

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
            .anyRequest().authenticated();

        http.formLogin()
            .loginPage("/login-page")
            .usernameParameter("loginId")
            .passwordParameter("password")
            .loginProcessingUrl("/login")
            .successHandler(loginSuccessHandler);

        http.logout()
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .deleteCookies("SESSION")
            .logoutSuccessHandler(tokenInvalidationHandler);

        http.authenticationProvider(jwtAuthenticationProvider);

        http.sessionManagement()
            .sessionFixation()
            .newSession();

        http.httpBasic()
            .disable();
        http.csrf()
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
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_BUSINESS > ROLE_CUSTOMER");
        return roleHierarchy;
    }

    @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }
}
