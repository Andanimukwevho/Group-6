package com.Future_Transitions.Future_Transitions.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "application_table")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Long jobId;

    private Province province;

    private String applicationName;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private String cvPath;
    private String idDocumentPath;
    private String coverLetterPath;

    private LocalDate appliedDate;

    @NotNull(message = "maxAge is required")
    private Integer maxAge;

    @NotNull(message = "minAge is required")
    private Integer minAge;

    @NotNull(message = "minExperience is required")
    private Integer minExperience;

    @NotNull(message = "minGpa is required")
    private Double minGpa;

    @Transient
    private List<String> candidateQualifications;

    @Column(name = "candidate_qualifications")
    private String candidateQualificationsRaw;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private User applicant;

    @ManyToOne
    @JoinColumn(name = "job_opening_id")
    private JobOpening jobOpening;

    // Getters & Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getCvPath() {
        return cvPath;
    }

    public void setCvPath(String cvPath) {
        this.cvPath = cvPath;
    }

    public String getIdDocumentPath() {
        return idDocumentPath;
    }

    public void setIdDocumentPath(String idDocumentPath) {
        this.idDocumentPath = idDocumentPath;
    }

    public String getCoverLetterPath() {
        return coverLetterPath;
    }

    public void setCoverLetterPath(String coverLetterPath) {
        this.coverLetterPath = coverLetterPath;
    }

    public LocalDate getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(LocalDate appliedDate) {
        this.appliedDate = appliedDate;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMinExperience() {
        return minExperience;
    }

    public void setMinExperience(Integer minExperience) {
        this.minExperience = minExperience;
    }

    public Double getMinGpa() {
        return minGpa;
    }

    public void setMinGpa(Double minGpa) {
        this.minGpa = minGpa;
    }

    public List<String> getCandidateQualifications() {
        if (candidateQualifications == null && candidateQualificationsRaw != null) {
            return Arrays.stream(candidateQualificationsRaw.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
        }
        return candidateQualifications;
    }

    public void setCandidateQualifications(List<String> candidateQualifications) {
        this.candidateQualifications = candidateQualifications;
        this.candidateQualificationsRaw = candidateQualifications != null
                ? String.join(",", candidateQualifications)
                : null;
    }

    public String getCandidateQualificationsRaw() {
        return candidateQualificationsRaw;
    }

    public void setCandidateQualificationsRaw(String raw) {
        this.candidateQualificationsRaw = raw;
        this.candidateQualifications = raw != null
                ? Arrays.stream(raw.split(",")).map(String::trim).collect(Collectors.toList())
                : null;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public JobOpening getJobOpening() {
        return jobOpening;
    }

    public void setJobOpening(JobOpening jobOpening) {
        this.jobOpening = jobOpening;
    }
}
