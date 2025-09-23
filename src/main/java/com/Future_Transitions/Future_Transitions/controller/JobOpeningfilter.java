//package com.Future_Transitions.Future_Transitions.controller;
//
//
//import com.Future_Transitions.Future_Transitions.model.JobOpeningElastic;
//import com.Future_Transitions.Future_Transitions.repository.JobOpeningElasticRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.StreamSupport;
//
//@RestController
//@RequestMapping("/api/jobs")
//@RequiredArgsConstructor
//
//public class JobOpeningfilter {
//
//
//    private final JobOpeningElasticRepository jobRepository;
//
//    @GetMapping("/search")
//    public List<JobOpeningElastic> searchJobs(
//            @RequestParam(required = false) String title,
//            @RequestParam(required = false) String createdBy,
//            @RequestParam(required = false) Integer maxAge,
//            @RequestParam(required = false) Integer minExperience,
//            @RequestParam(required = false) Double minGpa,
//            @RequestParam(required = false) String qualification
//    ) {
//        if (title != null) return jobRepository.findByTitleContainingIgnoreCase(title);
//        if (createdBy != null) return jobRepository.findByCreatedBy(createdBy);
//        if (maxAge != null) return jobRepository.findByMaxAgeGreaterThanEqual(maxAge);
//        if (minExperience != null) return jobRepository.findByMinExperienceGreaterThanEqual(minExperience);
//        if (minGpa != null) return jobRepository.findByMinGpaGreaterThanEqual(minGpa);
//        if (qualification != null) return jobRepository.findByRequiredQualificationsContainingIgnoreCase(qualification);
//
//        return StreamSupport.stream(jobRepository.findAll().spliterator(), false)
//                .collect(Collectors.toList());
//    }
//}
