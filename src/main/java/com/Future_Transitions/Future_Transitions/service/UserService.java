package com.Future_Transitions.Future_Transitions.service;



import com.Future_Transitions.Future_Transitions.model.User;
import com.Future_Transitions.Future_Transitions.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow();
    }



//    public User createUser(User user) {
//        return userRepository.save(user);
//    }
//
//
//    public List<User> getallUsers() {
//        return userRepository.findAll();
//    }
//
//    public User getUserById(Long id){
//        return userRepository.findById(id)
//        .orElseThrow(()-> new RuntimeException("Student not found" + id));
//    }
}


