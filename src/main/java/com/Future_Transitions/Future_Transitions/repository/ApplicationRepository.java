package com.Future_Transitions.Future_Transitions.repository;

import com.Future_Transitions.Future_Transitions.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ApplicationRepository  extends JpaRepository<Application , Long> {

    Optional<Application> findById(long id);
}
