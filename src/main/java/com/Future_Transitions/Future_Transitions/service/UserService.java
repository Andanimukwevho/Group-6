package com.Future_Transitions.Future_Transitions.service;


import com.Future_Transitions.Future_Transitions.model.User;
import com.Future_Transitions.Future_Transitions.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user){
       return userRepository.save(user);
    }
}
