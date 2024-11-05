package com.sales.shopapp.configuration;


import com.sales.shopapp.constant.Roles;
import com.sales.shopapp.filter.JwtTokenFilter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

@RequiredArgsConstructor
@Configuration
@EnableWebMvc
@EnableWebSecurity
@EnableMethodSecurity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebSecurityConfig {

    @NonFinal
    @Value("${api.prefix}")
    String apiPrefix;

    JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("*"));
                    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                    configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                    configuration.setExposedHeaders(List.of("x-auth-token"));
                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", configuration);
                    httpSecurityCorsConfigurer.configurationSource(source);
                })
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(
                                String.format("%s/users/register", apiPrefix),
                                String.format("%s/users/login", apiPrefix),
                                String.format("%s/healthcheck/**", apiPrefix),
                                String.format("%s/actuator/health", apiPrefix),
                                String.format("%s/actuator/info", apiPrefix),
                                "actuator/**",
                                "test/**",
                                //swagger
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/api-docs",
                                "/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/webjars/swagger-ui/**",
                                "/swagger-ui/index.html"
                        ).permitAll()
                        .requestMatchers(String.format("%s/actuator/**", apiPrefix)).hasRole("ACTUATOR_ADMIN")
                        .requestMatchers(GET, String.format("%s/roles**", apiPrefix)).permitAll()
                        .requestMatchers(GET, String.format("%s/categories**", apiPrefix)).permitAll()
                        .requestMatchers(POST, String.format("%s/categories**", apiPrefix)).hasRole(Roles.ADMIN)
                        .requestMatchers(PUT, String.format("%s/categories/**", apiPrefix)).hasAnyRole(Roles.ADMIN)
                        .requestMatchers(DELETE, String.format("%s/categories/**", apiPrefix)).hasAnyRole(Roles.ADMIN)
                        .requestMatchers(GET, String.format("%s/products**", apiPrefix)).permitAll()
                        .requestMatchers(GET, String.format("%s/roles**", apiPrefix)).permitAll()
                        .requestMatchers(GET, String.format("%s/categories**", apiPrefix)).permitAll()
                        .requestMatchers(GET, String.format("%s/products**", apiPrefix)).permitAll()
                        .requestMatchers(GET, String.format("%s/products/**", apiPrefix)).permitAll()
                        .requestMatchers(GET, String.format("%s/products/images/*", apiPrefix)).permitAll()
                        .requestMatchers(POST, String.format("%s/products**", apiPrefix)).hasAnyRole(Roles.USER) // admin
                        .requestMatchers(PUT, String.format("%s/products/**", apiPrefix)).hasAnyRole(Roles.ADMIN)
                        .requestMatchers(DELETE, String.format("%s/products/**", apiPrefix)).hasAnyRole(Roles.ADMIN)
                        .requestMatchers(POST, String.format("%s/orders/**", apiPrefix)).hasAnyRole(Roles.USER)
                        .requestMatchers(GET, String.format("%s/orders/**", apiPrefix)).permitAll()
                        .requestMatchers(PUT, String.format("%s/orders/**", apiPrefix)).hasRole(Roles.ADMIN)
                        .requestMatchers(DELETE, String.format("%s/orders/**", apiPrefix)).hasRole(Roles.ADMIN)
                        .requestMatchers(POST, String.format("%s/order_details/**", apiPrefix)).hasAnyRole(Roles.USER)
                        .requestMatchers(GET, String.format("%s/order_details/**", apiPrefix)).permitAll()
                        .requestMatchers(PUT, String.format("%s/order_details/**", apiPrefix)).hasRole(Roles.ADMIN)
                        .requestMatchers(DELETE, String.format("%s/order_details/**", apiPrefix)).hasRole(Roles.ADMIN)
                        .anyRequest().authenticated()
                );

//        http.securityMatcher(String.valueOf(EndpointRequest.toAnyEndpoint()));
        return http.build();
    }
}
