package com.Future_Transitions.Future_Transitions.controller;

import com.Future_Transitions.Future_Transitions.model.Application;
import com.Future_Transitions.Future_Transitions.model.ApplicationStatus;
import com.Future_Transitions.Future_Transitions.model.JobOpening;
import com.Future_Transitions.Future_Transitions.model.User;
import com.Future_Transitions.Future_Transitions.service.ApplicationService;
import com.Future_Transitions.Future_Transitions.service.FileStorageService;
import com.Future_Transitions.Future_Transitions.service.Imp.UserServiceImp;
import com.Future_Transitions.Future_Transitions.service.JobOpeningService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;

@RestController
@RequestMapping("api/auth")
public class ApplicationController {

    private final FileStorageService fileStorageService;
    private final UserServiceImp userServiceImp;
    private final JobOpeningService jobOpeningService;
    private final ApplicationService applicationService;

    public ApplicationController(FileStorageService fileStorageService, UserServiceImp userServiceImp, JobOpeningService jobOpeningService, ApplicationService applicationService) {
        this.fileStorageService = fileStorageService;
        this.userServiceImp = userServiceImp;
        this.jobOpeningService = jobOpeningService;
        this.applicationService = applicationService;
    }

    @PostMapping("/apply/{jobId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> applyToJob(@PathVariable Long jobId, @RequestParam("cv") MultipartFile cv,
                                        @RequestParam("id") MultipartFile idDoc,
                                        @RequestParam("coverLetter") MultipartFile coverLetter,
                                        Principal principal
    ) throws IOException {

        String email = principal.getName();
        User user = userServiceImp.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        JobOpening job = jobOpeningService.findById(jobId)
                .orElseThrow(() -> new UsernameNotFoundException("Job opening not found"));

        String cvPath = fileStorageService.storeFile(cv);
        String idPath = fileStorageService.storeFile(idDoc);
        String coverPath = fileStorageService.storeFile(coverLetter);

        Application application = new Application();
        application.setApplicant(user);
        application.setJobOpening(job);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCvPath(cvPath);
        application.setIdDocumentPath(idPath);
        application.setCoverLetterPath(coverPath);
        application.setAppliedDate(LocalDate.now());

        applicationService.createApplication(application);

        return ResponseEntity.ok("Application submitted");
    }
}
