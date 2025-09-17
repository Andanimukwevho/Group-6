package com.Future_Transitions.Future_Transitions.service;


import com.Future_Transitions.Future_Transitions.model.Application;
import java.util.Optional;


public interface ApplicationService {

   Application createApplication(Application application);
   Optional<Application> getApplicationById(Long id);
   Application updateApplication(Long id, Application updatedApplication);
   void deleteApplication(Long id);
}
