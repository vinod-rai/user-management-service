package com.cts.usermanagement.service;

import com.cts.usermanagement.dto.UserResponse;
import com.cts.usermanagement.exception.UserNotFoundException;
import com.cts.usermanagement.model.User;
import com.cts.usermanagement.model.UserReqBody;

import java.util.List;

public interface UserService {
    User saveUser(UserReqBody userRequest);

    User findUserById(Long userId) throws UserNotFoundException;

    List<User> findAllUsers();

    User updateUser(UserReqBody userRequest);

    User deleteUserByUserId(Long userId);
}
