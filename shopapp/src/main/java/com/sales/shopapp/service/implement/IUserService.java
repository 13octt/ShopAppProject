package com.sales.shopapp.service.implement;

import com.sales.shopapp.dto.UserDto;
import com.sales.shopapp.entity.User;

public interface IUserService {
    User createUser(UserDto userDto);

    User login(String userName, String password) throws Exception;

}
