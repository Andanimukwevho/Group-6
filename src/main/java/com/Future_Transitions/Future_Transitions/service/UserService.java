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
import java.util.Optional;


public interface UserService extends UserDetailsService {

    User create(User user);

   Optional<User> findByEmail(String email);

   List<User>getAllUsers();

   User updateUser(String email , User updateUser);

   User changePassword(String email , User changePassword);

   void deleteUser(String email);

   boolean existByEmail(String email);

}


