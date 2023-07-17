package store.cookshoong.www.cookshoongfrontend.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import store.cookshoong.www.cookshoongfrontend.auth.filter.JwtAuthenticationFilter;
import store.cookshoong.www.cookshoongfrontend.auth.provider.JwtAuthenticationProvider;

/**
 * 인증과 권한 기반으로 요청들을 검증을 하기 위한 시큐리티 설정 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.13
 */
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

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
            .mvcMatchers("/").permitAll()
            .mvcMatchers("/assets/**").permitAll()
            .mvcMatchers("/login-page").permitAll()
            .mvcMatchers("/sign-up").permitAll()
            .mvcMatchers("/sign-up-choice").permitAll()
            .anyRequest().authenticated();

        http.formLogin()
            .loginPage("/login-page")
            .usernameParameter("loginId")
            .passwordParameter("password")
            .loginProcessingUrl("/login");

        http.authenticationProvider(jwtAuthenticationProvider);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement()
            .sessionFixation()
            .newSession();

        http.httpBasic()
            .disable();
        http.csrf()
            .disable();
        return http.build();
    }
}
