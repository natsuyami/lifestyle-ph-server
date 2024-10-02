package com.lifestyle.ph.login.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final WebClient userInfoClient;

    private static final String[] MATCHER_PATTERN = {"/", "/myapp", "/auth/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .exceptionHandling(customizer -> customizer.authenticationEntryPoint(
                        new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(MATCHER_PATTERN).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(c -> c.opaqueToken(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public OpaqueTokenIntrospector introspector() {
        return new GoogleOpaqueTokenIntrospector(userInfoClient);
    }

    @Bean
    public FilterRegistrationBean<CustomHeaderAuthenticationFilter> authenticationPaymentFiler() {
        FilterRegistrationBean<CustomHeaderAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomHeaderAuthenticationFilter());
        registrationBean.addUrlPatterns("/myapp/videos/*"); // Specify the URL pattern for which the filter should be applied
        return registrationBean;
    }

}
