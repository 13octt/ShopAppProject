package com.sales.shopapp.configuration;

import com.sales.shopapp.enums.RoleEnum;
import com.sales.shopapp.filter.JwtTokenFilter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebSecurityConfig {

    JwtTokenFilter jwtTokenFilter;

    @Value("${api.prefix}")
    String apiPrefix;

    String[] publicEndpoints = {
            String.format("%s/users/register", apiPrefix),
            String.format("%s/users/login", apiPrefix)
    };

    String[] adminEndpoints = {
            String.format("%s/categories/**", apiPrefix),
            String.format("%s/products/**", apiPrefix),
            String.format("%s/orders/**", apiPrefix),
            String.format("%s/order_details/**", apiPrefix)
    };

    String[] userEndpoints = {
            String.format("%s/categories", apiPrefix),
            String.format("%s/products", apiPrefix),
            String.format("%s/orders/**", apiPrefix),
            String.format("%s/order_details/**", apiPrefix)
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(publicEndpoints).permitAll()
                        .requestMatchers(HttpMethod.POST, adminEndpoints).hasRole(RoleEnum.ADMIN.toString())
                        .requestMatchers(HttpMethod.GET, userEndpoints).hasAnyRole(
                                RoleEnum.ADMIN.toString(), RoleEnum.USER.toString())
                        .requestMatchers(HttpMethod.PUT, adminEndpoints).hasRole(RoleEnum.ADMIN.toString())
                        .requestMatchers(HttpMethod.DELETE, adminEndpoints).hasRole(RoleEnum.ADMIN.toString())
                        .anyRequest().authenticated())
                .build();
    }
}
