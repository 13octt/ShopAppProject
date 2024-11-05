package com.sales.shopapp.controller;

import com.sales.shopapp.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/tokens")
@RequiredArgsConstructor
public class TokenController {

//    @GetMapping("")
//    public ResponseEntity<List<Token>> getAllToken() {
//        List<Token> tokenList =
//    }
}
