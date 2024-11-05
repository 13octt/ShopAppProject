package com.sales.identity.service.implement;

import com.sales.identity.dto.UserDto;
import com.sales.identity.entity.User;

public interface IUserService {
    User createUser(UserDto userDto) throws Exception;

    String login(String userName, String password) throws Exception;

}
