package com.sales.shopapp.controller;

import com.sales.shopapp.dto.UserDto;
import com.sales.shopapp.dto.UserLoginDto;
import com.sales.shopapp.service.implement.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDto userDto,
            BindingResult result)
    {
        try {
            if (result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(fieldError -> fieldError.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            if (!userDto.getPassword().equals(userDto.getRetypePassword())) {
                return ResponseEntity.badRequest().body("Password does not match");
            }
            userService.createUser(userDto);
            return ResponseEntity.ok("Register successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDto userLoginDto) {
        String token = String.valueOf(userService.login(userLoginDto.getPhoneNumber(), userLoginDto.getPassword()));
        return ResponseEntity.ok(token);
    }
}
