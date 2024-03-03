package com.sales.shopapp.component;

import com.sales.shopapp.entity.User;
import com.sales.shopapp.exception.DataNotFoundException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

//    @Value("${jwt.expiration")
//    private Long expiration;
//    @Value("${jwt.secretKey")
//    private String secretKey;
//    public String generateToken(User user) throws Exception{
//        Map<String, Object> claims = new HashMap<>();
//        try{
//            String token = Jwts.builder()
//                    .setClaims(claims)
//                    .setSubject(user.getPhoneNumber())
//                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
//                    .signWith()
//                    .compact();
//        } catch (Exception e)
//        {
//            throw new DataNotFoundException("Failed to generate token");
//        }
//        return null;
//    }
//
//    private Key getSignInKey() {
//        byte[] bytes =
//    }

}
