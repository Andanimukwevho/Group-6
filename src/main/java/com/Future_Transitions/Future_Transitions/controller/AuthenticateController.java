package com.Future_Transitions.Future_Transitions.controller;


import com.Future_Transitions.Future_Transitions.dto.*;
import com.Future_Transitions.Future_Transitions.model.JobOpening;
import com.Future_Transitions.Future_Transitions.model.User;
import com.Future_Transitions.Future_Transitions.service.ApplicationService;
import com.Future_Transitions.Future_Transitions.service.FileStorageService;
import com.Future_Transitions.Future_Transitions.service.Imp.AuthenticationServiceImp;
import com.Future_Transitions.Future_Transitions.service.Imp.UserServiceImp;
import com.Future_Transitions.Future_Transitions.service.JobOpeningService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/api/auth")
@CrossOrigin("*")

@RestController
public class AuthenticateController {

    private final AuthenticationServiceImp authenticationServiceImp;
    private final JobOpeningService jobOpeningService;
    private final ApplicationService applicationService;
    private final UserServiceImp userServiceImp;
    private final FileStorageService fileStorageService;

    public AuthenticateController(AuthenticationServiceImp authenticationServiceImp, JobOpeningService jobOpeningService, ApplicationService applicationService, UserServiceImp userServiceImp, FileStorageService fileStorageService) {
        this.authenticationServiceImp = authenticationServiceImp;
        this.jobOpeningService = jobOpeningService;
        this.applicationService = applicationService;
        this.userServiceImp = userServiceImp;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/register")
    public ResponseEntity<RequestResponse> register(@Valid @RequestBody RegisterDTO registerDTO) {
        return ResponseEntity.ok(authenticationServiceImp.register(registerDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDTO) {
        LoginResponse response = authenticationServiceImp.login(loginDTO);

        if (response.getStatusCode() == 200) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

    }

    @PostMapping("/refresh")
    public ResponseEntity<RequestResponse> refreshToken(@RequestBody RefreshedTokenRequest refreshedTokenRequest) {
        return ResponseEntity.ok(authenticationServiceImp.refreshToken(refreshedTokenRequest));
    }

    @GetMapping("/getalluser")
    public ResponseEntity<RequestResponse> getAllUsers() {
        return ResponseEntity.ok(authenticationServiceImp.getAllUsers());
    }

    @GetMapping("/useremail")
    public ResponseEntity<RequestResponse> getUserEmail(@PathVariable String email) {
        return ResponseEntity.ok(authenticationServiceImp.getUsersByEmail(email));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<RequestResponse> deleteUser(@PathVariable String email) {
        return ResponseEntity.ok(authenticationServiceImp.deleteUser(email));
    }

    @GetMapping("/api/admin/get-users/{userId}")
    public ResponseEntity<RequestResponse> getUSerByEmail(@PathVariable String email) {
        return ResponseEntity.ok(authenticationServiceImp.getUsersByEmail(email));

    }

    @PutMapping("/api/admin/update/{userEmail}")
    public ResponseEntity<RequestResponse> updateUser(@PathVariable String email, @RequestBody User user) {
        return ResponseEntity.ok(authenticationServiceImp.updateUser(email, user));
    }

    @GetMapping("/api/admin/get-profile")
    public ResponseEntity<RequestResponse> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        RequestResponse response = authenticationServiceImp.getMyInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/api/admin/create/post")
    public ResponseEntity<JobOpening> createJobs(@RequestBody JobOpening jobOpening, Authentication authentication) {
        User admin = userServiceImp.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        JobOpening createJob = jobOpeningService.createJob(jobOpening, admin);
        return new ResponseEntity<>(createJob, HttpStatus.CREATED);
    }

    @PutMapping("/api/admin/update/post")
    public ResponseEntity<JobOpening> updateJobs(@PathVariable long id, @RequestBody JobOpening jobOpening) {
        JobOpening updatedJob = jobOpeningService.updateJob(id, jobOpening);
        return ResponseEntity.ok(updatedJob);
    }

    @DeleteMapping("/api/admin/delete/post/{id}")
    public ResponseEntity<JobOpening> deleteJob(@PathVariable Long id) {
        JobOpening jobToDelete = jobOpeningService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found with id: " + id));

        jobOpeningService.deleteJob(id);
        return ResponseEntity.ok(jobToDelete);
    }
}