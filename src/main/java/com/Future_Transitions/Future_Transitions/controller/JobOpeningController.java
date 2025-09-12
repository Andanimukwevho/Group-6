package com.Future_Transitions.Future_Transitions.controller;


import com.Future_Transitions.Future_Transitions.model.JobOpening;
import com.Future_Transitions.Future_Transitions.model.User;
import com.Future_Transitions.Future_Transitions.service.JobOpeningService;
import com.Future_Transitions.Future_Transitions.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class JobOpeningController {

    private final JobOpeningService jobOpeningService;
    private final UserService userService;

    public JobOpeningController(JobOpeningService jobOpeningService, UserService userService) {
        this.jobOpeningService = jobOpeningService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createJob(@Valid @RequestBody JobOpening job, Authentication authentication) {

        User admin = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        JobOpening savedJob = jobOpeningService.createJob(job, admin);

        return new ResponseEntity<>(savedJob, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJob(@PathVariable Long id, @RequestBody JobOpening job) {
        JobOpening updated = jobOpeningService.updateJob(id, job);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        jobOpeningService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<JobOpening>> getAllJobs() {
        return ResponseEntity.ok(jobOpeningService.findAll());
    }

}
