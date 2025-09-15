package com.Future_Transitions.Future_Transitions.service;

import com.Future_Transitions.Future_Transitions.model.JobOpening;
import com.Future_Transitions.Future_Transitions.model.User;
import java.util.List;
import java.util.Optional;

public interface JobOpeningService {

    JobOpening createJob(JobOpening jobOpening, User admin);
    JobOpening updateJob(Long id, JobOpening updatedJob);
    void deleteJob(Long id);
    Optional<JobOpening> findById(Long id);
    List<JobOpening> findAll();

}
