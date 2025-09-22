//package com.Future_Transitions.Future_Transitions.repository;
//
//import com.Future_Transitions.Future_Transitions.model.JobOpeningElastic;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//import java.util.List;
//
//public interface JobOpeningElasticRepository extends ElasticsearchRepository<JobOpeningElastic, String> {
//
//    List<JobOpeningElastic> findByTitleContainingIgnoreCase(String keyword);
//
//    List<JobOpeningElastic> findByCreatedBy(String username);
//
//    // Filter by maximum allowed candidate age
//    List<JobOpeningElastic> findByMaxAgeGreaterThanEqual(int maxAge);
//
//    // Filter by minimum experience years required
//    List<JobOpeningElastic> findByMinExperienceGreaterThanEqual(int minExperience);
//
//    // Filter by minimum GPA required
//    List<JobOpeningElastic> findByMinGpaGreaterThanEqual(double minGpa);
//
//    // Filter by qualifications (partial match)
//    List<JobOpeningElastic> findByRequiredQualificationsContainingIgnoreCase(String qualification);
//
//    // Combine filters
//    List<JobOpeningElastic> findByMaxAgeGreaterThanEqualAndMinExperienceGreaterThanEqual(int maxAge, int minExperience);
//}
