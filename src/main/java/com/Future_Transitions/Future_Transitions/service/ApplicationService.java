package com.Future_Transitions.Future_Transitions.service;


import com.Future_Transitions.Future_Transitions.model.Application;
import com.Future_Transitions.Future_Transitions.model.User;

import java.util.List;
import java.util.Optional;


public interface ApplicationService {

   Application createApplication(Application application);
   Optional<Application> getApplicationById(long id);
   Application updateApplication(long id, Application updatedApplication);
   void deleteApplication(long id);
   Optional<Application> findByFileName(String filename);
   List<Application> getApplicationsByUser(User user);
}
