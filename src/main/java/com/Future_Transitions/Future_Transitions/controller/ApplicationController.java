package com.Future_Transitions.Future_Transitions.controller;

import com.Future_Transitions.Future_Transitions.model.*;
import com.Future_Transitions.Future_Transitions.service.ApplicationService;
import com.Future_Transitions.Future_Transitions.service.FileStorageService;
import com.Future_Transitions.Future_Transitions.service.Imp.UserServiceImp;
import com.Future_Transitions.Future_Transitions.service.JobOpeningService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("api/auth")
@CrossOrigin("*")
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
    public ResponseEntity<?> applyToJob(@PathVariable Long jobId,
                                        @RequestParam("cv") MultipartFile cv,
                                        @RequestParam("id") MultipartFile idDoc,
                                        @RequestParam("coverLetter") MultipartFile coverLetter,
                                        @RequestParam("applicationName") String applicationName,
                                        @RequestParam("province") Province province,
                                        @RequestParam("maxAge") Integer maxAge,
                                        @RequestParam("minAge") Integer minAge,
                                        @RequestParam("minExperience") Integer minExperience,
                                        @RequestParam("minGpa") Double minGpa,
                                        @RequestParam("candidateQualifications") String candidateQualificationsJson,
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

        // Parse candidateQualifications JSON string into List<String>
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> candidateQualifications = objectMapper.readValue(candidateQualificationsJson, new TypeReference<List<String>>() {});

        Application application = new Application();
        application.setApplicant(user);
        application.setJobOpening(job);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCvPath(cvPath);
        application.setIdDocumentPath(idPath);
        application.setCoverLetterPath(coverPath);
        application.setAppliedDate(LocalDate.now());

        application.setApplicationName(applicationName);
        application.setProvince(province);
        application.setMaxAge(maxAge);
        application.setMinAge(minAge);
        application.setMinExperience(minExperience);
        application.setMinGpa(minGpa);
        application.setCandidateQualifications(candidateQualifications);

        applicationService.createApplication(application);

        return ResponseEntity.ok("Application submitted");
    }
    @GetMapping("/download/{filename:.+}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename, Principal principal) {
        User currentUser = userServiceImp.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Optional<Application> appOpt = applicationService.findByFileName(filename);

        if (appOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not linked to any application");
        }

        Application application = appOpt.get();

        boolean isOwner = application.getApplicant().getId()==(currentUser.getId());
        boolean isAdmin = currentUser.getRole() == Role.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this file");
        }

        Resource resource = fileStorageService.loadFileAsResource(filename);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
