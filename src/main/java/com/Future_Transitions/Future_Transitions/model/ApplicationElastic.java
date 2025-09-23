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
//@Document(indexName = "applications")
//public class ApplicationElastic {
//
//    @Id
//    private String id;
//
//    private String jobId; // JobOpening ID
//    private String applicationName;
//    private String province;
//    private String status;
//    private String cvPath;
//    private String idDocumentPath;
//    private String coverLetterPath;
//    private LocalDate appliedDate;
//    private Integer maxAge;
//    private Integer minExperience;
//    private Double minGpa;
//
//    private List<String> candidateQualifications;
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
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
//    public String getJobId() {
//        return jobId;
//    }
//
//    public void setJobId(String jobId) {
//        this.jobId = jobId;
//    }
//
//    public String getApplicationName() {
//        return applicationName;
//    }
//
//    public void setApplicationName(String applicationName) {
//        this.applicationName = applicationName;
//    }
//
//    public String getProvince() {
//        return province;
//    }
//
//    public void setProvince(String province) {
//        this.province = province;
//    }
//
//    public String getCvPath() {
//        return cvPath;
//    }
//
//    public void setCvPath(String cvPath) {
//        this.cvPath = cvPath;
//    }
//
//    public String getIdDocumentPath() {
//        return idDocumentPath;
//    }
//
//    public void setIdDocumentPath(String idDocumentPath) {
//        this.idDocumentPath = idDocumentPath;
//    }
//
//    public String getCoverLetterPath() {
//        return coverLetterPath;
//    }
//
//    public void setCoverLetterPath(String coverLetterPath) {
//        this.coverLetterPath = coverLetterPath;
//    }
//
//    public LocalDate getAppliedDate() {
//        return appliedDate;
//    }
//
//    public void setAppliedDate(LocalDate appliedDate) {
//        this.appliedDate = appliedDate;
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
//    public List<String> getCandidateQualifications() {
//        return candidateQualifications;
//    }
//
//    public void setCandidateQualifications(List<String> candidateQualifications) {
//        this.candidateQualifications = candidateQualifications;
//    }
//}