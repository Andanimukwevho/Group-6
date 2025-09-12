package com.Future_Transitions.Future_Transitions.service;


import com.Future_Transitions.Future_Transitions.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
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


