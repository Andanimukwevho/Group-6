//package com.Future_Transitions.Future_Transitions.model;
//
//
//import jakarta.persistence.Id;
//import lombok.Builder;
//import org.springframework.data.elasticsearch.annotations.Document;
//import java.time.LocalDate;
//import java.util.List;
//
//@Builder
//@Document(indexName = "job_openings")
//public class JobOpeningElastic {
//
//
//    @Id
//    private String id;
//
//    private String title;
//    private String description;
//    private Integer maxAge;
//    private Integer minAge;
//    private Integer minExperience;
//    private Double minGpa;
//    private List<String> requiredQualifications;
//    private LocalDate postedDate;
//    private String createdBy;
//    private int applicationCount;
//
//    public LocalDate getPostedDate() {
//        return postedDate;
//    }
//
//    public void setPostedDate(LocalDate postedDate) {
//        this.postedDate = postedDate;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public Integer getMaxAge() {
//        return maxAge;
//    }
//
//    public void setMaxAge(Integer maxAge) {
//        this.maxAge = maxAge;
//    }
//
//    public Integer getMinExperience() {
//        return minExperience;
//    }
//
//    public void setMinExperience(Integer minExperience) {
//        this.minExperience = minExperience;
//    }
//
//    public Double getMinGpa() {
//        return minGpa;
//    }
//
//    public void setMinGpa(Double minGpa) {
//        this.minGpa = minGpa;
//    }
//
//    public List<String> getRequiredQualifications() {
//        return requiredQualifications;
//    }
//
//    public void setRequiredQualifications(List<String> requiredQualifications) {
//        this.requiredQualifications = requiredQualifications;
//    }
//
//    public String getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(String createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    public int getApplicationCount() {
//        return applicationCount;
//    }
//
//    public void setApplicationCount(int applicationCount) {
//        this.applicationCount = applicationCount;
//    }
//    public Integer getMinAge() {
//        return minAge;
//    }
//
//    public void setMinAge(Integer minAge) {
//        this.minAge = minAge;
//    }
//
//}
//
//
//
