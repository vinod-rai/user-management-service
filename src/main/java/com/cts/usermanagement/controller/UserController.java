package com.cts.usermanagement.controller;

import com.cts.usermanagement.exception.UserNotFoundException;
import com.cts.usermanagement.model.User;
import com.cts.usermanagement.model.UserReqBody;
import com.cts.usermanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userServiceImpl;

    public UserController(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/user")
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserReqBody userRequest) {
        User response = userServiceImpl.saveUser(userRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<User> findUserById(@PathVariable("userId") Long userId)
            throws UserNotFoundException {
        User user = userServiceImpl.findUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userServiceImpl.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/user")
    public ResponseEntity<User> updateUser(@RequestBody @Valid UserReqBody userRequest) {
        if (userRequest.getUserId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userServiceImpl.updateUser(userRequest);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<User> deleteUserByUserId(@PathVariable("userId") Long userId) {
        User user = userServiceImpl.deleteUserByUserId(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
