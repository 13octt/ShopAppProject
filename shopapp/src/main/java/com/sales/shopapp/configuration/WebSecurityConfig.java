package com.sales.shopapp.configuration;

import com.sales.shopapp.enums.RoleEnum;
import com.sales.shopapp.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.http.HttpMethod.*;

@Configuration
//@EnableMethodSecurity
@EnableWebSecurity
@EnableWebMvc
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    @Value("${api.prefix}")
    private String apiPrefix;
    @Bean
    //Pair.of(String.format("%s/products", apiPrefix), "GET"),
    public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers(
                                    String.format("%s/users/register", apiPrefix),
                                    String.format("%s/users/login", apiPrefix)
                            )
                            .permitAll()

                            .requestMatchers(GET,
                                    String.format("%s/categories**", apiPrefix)).hasAnyRole(RoleEnum.USER.toString(), RoleEnum.ADMIN.toString())

                            .requestMatchers(POST,
                                    String.format("%s/categories/**", apiPrefix)).hasAnyRole(RoleEnum.ADMIN.toString())

                            .requestMatchers(PUT,
                                    String.format("%s/categories/**", apiPrefix)).hasAnyRole(RoleEnum.ADMIN.toString())

                            .requestMatchers(DELETE,
                                    String.format("%s/categories/**", apiPrefix)).hasAnyRole(RoleEnum.ADMIN.toString())

                            .requestMatchers(GET,
                                    String.format("%s/products**", apiPrefix)).hasAnyRole(RoleEnum.USER.toString(), RoleEnum.ADMIN.toString())

                            .requestMatchers(POST,
                                    String.format("%s/products/**", apiPrefix)).hasAnyRole(RoleEnum.ADMIN.toString())

                            .requestMatchers(PUT,
                                    String.format("%s/products/**", apiPrefix)).hasAnyRole(RoleEnum.ADMIN.toString())

                            .requestMatchers(DELETE,
                                    String.format("%s/products/**", apiPrefix)).hasAnyRole(RoleEnum.ADMIN.toString())


                            .requestMatchers(POST,
                                    String.format("%s/orders/**", apiPrefix)).hasAnyRole(RoleEnum.USER.toString())

                            .requestMatchers(GET,
                                    String.format("%s/orders/**", apiPrefix)).hasAnyRole(RoleEnum.USER.toString(), RoleEnum.ADMIN.toString())

                            .requestMatchers(PUT,
                                    String.format("%s/orders/**", apiPrefix)).hasRole(RoleEnum.ADMIN.toString())

                            .requestMatchers(DELETE,
                                    String.format("%s/orders/**", apiPrefix)).hasRole(RoleEnum.ADMIN.toString())

                            .requestMatchers(POST,
                                    String.format("%s/order_details/**", apiPrefix)).hasAnyRole(RoleEnum.USER.toString())

                            .requestMatchers(GET,
                                    String.format("%s/order_details/**", apiPrefix)).hasAnyRole(RoleEnum.USER.toString(), RoleEnum.ADMIN.toString())

                            .requestMatchers(PUT,
                                    String.format("%s/order_details/**", apiPrefix)).hasRole(RoleEnum.ADMIN.toString())

                            .requestMatchers(DELETE,
                                    String.format("%s/order_details/**", apiPrefix)).hasRole(RoleEnum.ADMIN.toString())

                            .anyRequest().authenticated();

                })

        ;
        return http.build();
    }
}