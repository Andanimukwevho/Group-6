package com.Future_Transitions.Future_Transitions.service.Imp;


import com.Future_Transitions.Future_Transitions.model.Application;
import com.Future_Transitions.Future_Transitions.model.User;
import com.Future_Transitions.Future_Transitions.repository.ApplicationRepository;
import com.Future_Transitions.Future_Transitions.service.ApplicationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationImp implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationImp(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Application createApplication(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public Optional<Application> getApplicationById(long id) {
        return applicationRepository.findById(id);
    }

    @Override
    public Application updateApplication(long id, Application updatedApplication) {
        Application existing = applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Application not found with id: " + id));

        existing.setStatus(updatedApplication.getStatus());
        existing.setCvPath(updatedApplication.getCvPath());
        existing.setMaxAge(updatedApplication.getMaxAge());
        existing.setMinAge(updatedApplication.getMinAge());
        existing.setMinExperience(updatedApplication.getMinExperience());
        existing.setMinGpa(updatedApplication.getMinGpa());
        existing.setCoverLetterPath(updatedApplication.getCoverLetterPath());
        existing.setIdDocumentPath(updatedApplication.getIdDocumentPath());
        existing.setApplicationName(updatedApplication.getApplicationName());
        existing.setProvince(updatedApplication.getProvince());
        existing.setCandidateQualifications(updatedApplication.getCandidateQualifications());

        return applicationRepository.save(existing);
    }

    @Override
    public void deleteApplication(long id) {
        if (!applicationRepository.existsById(id)) {
            throw new EntityNotFoundException("Application not found with id: " + id);
        }
        applicationRepository.deleteById(id);
    }
    @Override
    public Optional<Application> findByFileName(String filename) {
        return applicationRepository.findByCvPathOrIdDocumentPathOrCoverLetterPath(filename, filename, filename);
    }
    @Override
    public List<Application> getApplicationsByUser(User user) {
        return applicationRepository.findByApplicant(user);
    }


}
