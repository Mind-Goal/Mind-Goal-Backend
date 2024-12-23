package com.mindgoal.config;

import com.mindgoal.config.jwt.JwtAuthenticationFilter;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    public static final String API_V_1 = "/"; //"/api/v1/";
    private static final List<String> ORIGIN_PATTERN = List.of("*");
    private static final String CORS_CONFIGURATION_PATTERN = "/**";

    private static final List<String> ALLOWED_HEADERS = Arrays.asList("Origin", "Content-Type", "Accept", "Authorization", "X-Requested-With");
    private static final List<String> ALLOWED_METHODS = Arrays.asList("GET", "POST", "PUT", "DELETE");

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                                .requestMatchers(API_V_1 + "oauth/login").permitAll()
                                .requestMatchers(API_V_1 + "oauth/logout").authenticated()
                                .requestMatchers(API_V_1 + "chat/**").permitAll()
                                .requestMatchers(API_V_1 + "matching/**").permitAll()
                                .requestMatchers(API_V_1 + "payment/*/wishes").authenticated()
                                .requestMatchers(API_V_1 + "schedule/**").permitAll()
                                .requestMatchers(API_V_1 + "test/**").permitAll()
                                .requestMatchers(API_V_1 + "user/**").authenticated()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable).disable())
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> corsConfigurationSource())
//                .exceptionHandling( //todo 예외 핸들링 처리 토의 필요
//                        httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
//                                .authenticationEntryPoint(customAuthenticationEntryPoint)
//                                .accessDeniedHandler(authenticationAccessDeniedHandler)
//                )
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(ORIGIN_PATTERN);
        configuration.setAllowedHeaders(ALLOWED_HEADERS);
        configuration.setAllowedMethods(ALLOWED_METHODS);
        configuration.setAllowCredentials(true);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(CORS_CONFIGURATION_PATTERN, configuration);

        return source;
    }
}
