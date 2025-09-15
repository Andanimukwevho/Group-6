package com.Future_Transitions.Future_Transitions.service.Imp;


import com.Future_Transitions.Future_Transitions.model.Application;
import com.Future_Transitions.Future_Transitions.repository.ApplicationRepository;
import com.Future_Transitions.Future_Transitions.service.ApplicationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }

    @Override
    public Application updateApplication(Long id, Application updatedApplication) {
        Application existing = applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Application not found with id: " + id));

        existing.setStatus(updatedApplication.getStatus());
        existing.setCvPath(updatedApplication.getCvPath());
        existing.setCoverLetterPath(updatedApplication.getCoverLetterPath());
        existing.setIdDocumentPath(updatedApplication.getIdDocumentPath());

        return applicationRepository.save(existing);
    }

    @Override
    public void deleteApplication(Long id) {
        if (!applicationRepository.existsById(id)) {
            throw new EntityNotFoundException("Application not found with id: " + id);
        }
        applicationRepository.deleteById(id);
    }
}
