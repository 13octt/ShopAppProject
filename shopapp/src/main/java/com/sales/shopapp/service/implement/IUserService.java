package com.sales.shopapp.service.implement;

import com.sales.shopapp.dto.request.UserDto;
import com.sales.shopapp.entity.User;

public interface IUserService {
    User createUser(UserDto userDto) throws Exception;

    String login(String userName, String password) throws Exception;

}
