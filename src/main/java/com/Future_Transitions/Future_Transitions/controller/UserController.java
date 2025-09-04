package com.Future_Transitions.Future_Transitions.controller;


import com.Future_Transitions.Future_Transitions.model.User;
import com.Future_Transitions.Future_Transitions.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

//    @PostMapping("/create")
//    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
//        try {
//            User createUser = userService.createUser(user);
//            return new ResponseEntity(createUser, HttpStatus.CREATED);
//        } catch (DataIntegrityViolationException e) {
//            return new ResponseEntity("email already exist", HttpStatus.CONFLICT);
//        } catch (Exception e) {
//            return new ResponseEntity("Registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/getall")
//    public ResponseEntity<java.util.List<User>> getallUsers(){
//        List<User> users = userService.getallUsers();
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }
}
