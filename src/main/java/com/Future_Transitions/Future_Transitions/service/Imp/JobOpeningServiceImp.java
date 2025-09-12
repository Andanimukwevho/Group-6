package com.Future_Transitions.Future_Transitions.service.Imp;

import com.Future_Transitions.Future_Transitions.model.JobOpening;
import com.Future_Transitions.Future_Transitions.model.Role;
import com.Future_Transitions.Future_Transitions.model.User;
import com.Future_Transitions.Future_Transitions.repository.JobOpeningRepository;
import com.Future_Transitions.Future_Transitions.service.JobOpeningService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class JobOpeningServiceImp implements JobOpeningService {

    private final JobOpeningRepository jobOpeningRepository;

    public JobOpeningServiceImp(JobOpeningRepository jobOpeningRepository) {
        this.jobOpeningRepository = jobOpeningRepository;
    }

    @Transactional
    @Override
    public JobOpening createJob(JobOpening jobOpening, User admin) {

        if (admin.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Only admins can create job openings.");
        }

        jobOpening.setPostedDate(LocalDate.now());
        jobOpening.setCreatedBy(admin);

        JobOpening saved = jobOpeningRepository.save(jobOpening);

        return saved;
    }


    @Override
    public JobOpening updateJob(Long id, JobOpening updatedJob) {
        JobOpening existing = findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job not found with ID: " + id));
        existing.setTitle(updatedJob.getTitle());
        existing.setDescription(updatedJob.getDescription());
        return jobOpeningRepository.save(existing);
    }

    @Override
    public void deleteJob(Long id) {
        JobOpening job = findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Job not found with ID: " + id));
        jobOpeningRepository.delete(job);
    }

    @Override
    public Optional<JobOpening> findById(Long id) {
        return jobOpeningRepository.findById(id);
    }

    @Override
    public List<JobOpening> findAll() {
        return jobOpeningRepository.findAll();
    }
}
