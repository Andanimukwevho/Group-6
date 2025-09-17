package com.Future_Transitions.Future_Transitions.repository;

import com.Future_Transitions.Future_Transitions.model.ForgottenPasswordToken;
import com.Future_Transitions.Future_Transitions.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ForgottenPasswordTokenRepository extends JpaRepository<ForgottenPasswordToken , Long> {

    Optional<ForgottenPasswordToken> findByToken(String token);
    void deleteByUser(User user);
}
