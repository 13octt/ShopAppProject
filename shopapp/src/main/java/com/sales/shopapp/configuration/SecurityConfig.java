package com.sales.shopapp.configuration;

import com.sales.shopapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
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
//            return exis
//        }
//    }
//
}
