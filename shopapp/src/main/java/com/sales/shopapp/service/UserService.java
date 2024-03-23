package com.sales.shopapp.service;

import com.sales.shopapp.dto.UserDto;
import com.sales.shopapp.exception.DataNotFoundException;
import com.sales.shopapp.entity.Role;
import com.sales.shopapp.entity.User;
import com.sales.shopapp.repository.RoleRepository;
import com.sales.shopapp.repository.UserRepository;
import com.sales.shopapp.service.implement.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
//    private final PasswordEncoder passwordEncoder;

    @SneakyThrows
    @Override
    public User createUser(UserDto userDto) {
        String phoneNumber = userDto.getPhoneNumber();
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        User newUser = User.builder()
                .fullName(userDto.getFullName())
                .phoneNumber(userDto.getPhoneNumber())
                .password(userDto.getPassword())
                .address(userDto.getAddress())
                .dateOfBirth(userDto.getDateOfBirth())
                .facebookAccountId(userDto.getFacebookAccountId())
                .googleAccountId(userDto.getGoogleAccountId())
                .build();
        Role role = roleRepository.findById(userDto.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found"));
        newUser.setRoleId(role);

        if (userDto.getFacebookAccountId() == 0 && userDto.getGoogleAccountId() == 0) {
            String password = userDto.getPassword();
//            String encodedPassword = passwordEncoder.encode(password);
//            newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);
    }

    @Override
    public User login(String phoneNumber, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new DataNotFoundException("Invalid phone number/password");
        }
        return optionalUser.get();
    }
}
