package com.sales.shopapp.configuration;

import com.sales.shopapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return phoneNumber -> {
//            userRepository
//                    .findByPhoneNumber(phoneNumber)
//                    .orElseThrow(() -> new UsernameNotFoundException(
//                            "Can't find user with phone number = " + phoneNumber
//                    ));
//            return existingUser;
//        }
//    }

//    @Bean
//    @Deprecated(since = "6.1", forRemoval = true)
//    protected SecurityFilterChain configure (HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.authorizeHttpRequests()
//                .requestMatchers(HttpMethod.POST, "/users")
//                .permitAll();
//    }
}
