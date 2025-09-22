package com.Future_Transitions.Future_Transitions.repository;

import com.Future_Transitions.Future_Transitions.model.Application;
import com.Future_Transitions.Future_Transitions.model.JobOpening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobOpeningRepository extends JpaRepository<JobOpening , Long> {

    Optional<JobOpening> findById(long id);
}
