package com.sales.shopapp.service.implement;

import com.sales.shopapp.dto.UserDto;
import com.sales.shopapp.model.User;
import org.springframework.stereotype.Service;

public interface IUserService {
    User createUser(UserDto userDto);
    User login(String userName, String password);

}
