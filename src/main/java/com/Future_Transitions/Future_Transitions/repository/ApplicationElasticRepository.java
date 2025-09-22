//package com.Future_Transitions.Future_Transitions.repository;
//
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//
//import java.util.List;
//
//public interface ApplicationElasticRepository extends ElasticsearchRepository<ApplicationElasticRepository, String> {
//
//    List<ApplicationElasticRepository> findByJobId(String jobId);
//
//    List<ApplicationElasticRepository> findByApplicantNameContaining(String keyword);
//
//    // Filter candidates by age (greater or equal)
//    List<ApplicationElasticRepository> findByAgeGreaterThanEqual(int age);
//
//    // Filter candidates by experience years (greater or equal)
//    List<ApplicationElasticRepository> findByExperienceYearsGreaterThanEqual(int experienceYears);
//
//    // Filter candidates by GPA (greater or equal)
//    List<ApplicationElasticRepository> findByGpaGreaterThanEqual(double gpa);
//
//    // Filter candidates by qualification (containing string)
//    List<ApplicationElasticRepository> findByQualificationsContaining(String qualification);
//
//    // Combine filters example
//    List<ApplicationElasticRepository> findByAgeGreaterThanEqualAndExperienceYearsGreaterThanEqual(int age, int experienceYears);
//}
//
//
