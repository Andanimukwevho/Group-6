package com.Future_Transitions.Future_Transitions.repository;

import com.Future_Transitions.Future_Transitions.model.Application;
import com.Future_Transitions.Future_Transitions.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository  extends JpaRepository<Application , Long> {

    Optional<Application> findById(long id);

    List<Application> findByApplicant(User applicant);

    Optional<Application> findByCvPathOrIdDocumentPathOrCoverLetterPath(String cvPath, String idPath, String coverLetterPath);
}
