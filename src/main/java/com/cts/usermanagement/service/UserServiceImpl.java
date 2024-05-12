package com.cts.usermanagement.service;

import com.cts.usermanagement.dto.UserResponse;
import com.cts.usermanagement.exception.UserNotFoundException;
import com.cts.usermanagement.model.User;
import com.cts.usermanagement.model.UserReqBody;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static Long userId;
    private static Map<Long, User> userData;

    static {
        userId = 0L;
        userData = new HashMap<>();
    }


    @Override
    public User saveUser(UserReqBody userRequest) {

        User user = User.builder()
                .userId(++userId)
                .userName(userRequest.getUserName())
                .department(userRequest.getDepartment())
                .managerName(userRequest.getManagerName())
                .status("ACT")
                .build();

        userData.put(userId, user);

        return user;
    }

    @Override
    public User findUserById(Long userId) throws UserNotFoundException {
        Map.Entry<Long, User> userMap = userData.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(userId))
                .findFirst().orElseThrow(UserNotFoundException::new);
        return userMap.getValue();
    }


    @Override
    public List<User> findAllUsers() {

        List<User> users = userData.values()
                .stream()
                .toList();

        return users;
    }

    @Override
    public User updateUser(UserReqBody userRequest) {
        if(userData.containsKey(userRequest.getUserId())){
            userData.put(userRequest.getUserId(), User.builder()
                            .userId(userRequest.getUserId())
                            .userName(userRequest.getUserName())
                            .department(userRequest.getDepartment())
                            .managerName(userRequest.getManagerName())
                    .build());
        }
        return userData.get(userRequest.getUserId());
    }

    @Override
    public User deleteUserByUserId(Long userId) {
        User user = null;
        if(userData.containsKey(userId)) {
            user = userData.get(userId);
            userData.remove(userId);
        }
        return user;
    }
}
