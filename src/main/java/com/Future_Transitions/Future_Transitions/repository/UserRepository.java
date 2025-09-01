package com.Future_Transitions.Future_Transitions.repository;

import com.Future_Transitions.Future_Transitions.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
