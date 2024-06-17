package com.project.board.boardProject.config;

import com.project.board.boardProject.service.CustomOAuth2UserService;
import com.project.board.boardProject.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationSuccessHandler customSuccessHandler;
    private final AuthenticationFailureHandler customFailureHandler;
    private final CustomOAuth2UserService customOauth2UserService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((auth) -> {
                        auth.requestMatchers("/", "/post/list", "/post/detail/**", "/post/search", "/auth/**").permitAll();
                        auth.anyRequest().authenticated();
                    }
                )
                .formLogin((form) -> {
                    form.loginPage("/auth/login");
                    form.loginProcessingUrl("/auth/login");
                    form.defaultSuccessUrl("/");
                    form.successHandler(customSuccessHandler);
                    form.failureHandler(customFailureHandler);
                })
                .logout((logout) -> {
                    logout.logoutUrl("/auth/logout");
                    logout.logoutSuccessUrl("/");
                    logout.invalidateHttpSession(true);
                    logout.deleteCookies("JSESSIONID", "remember-me");
                })
                .oauth2Login((oauth2) -> { oauth2
                        .userInfoEndpoint(endpoint -> endpoint.userService(customOauth2UserService));
                })
                .build();
    }

}
