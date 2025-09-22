package com.Future_Transitions.Future_Transitions.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "job_opening")
public class JobOpening {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Description is mandatory")
    private String description;
    @NotNull(message = "maxAge is required")
    @Column(nullable = false, columnDefinition = "int default 36")
    private Integer maxAge;
    @NotNull(message = "minAge is required")
    @Column(nullable = false, columnDefinition = "int default 18")
    private Integer minAge;
    @NotNull(message = "minExperience is required")
    @Column(nullable = false, columnDefinition = "int default 3")
    private Integer minExperience;
    @NotNull(message = "minGpa is required")
    @Column(nullable = false, columnDefinition = "int default 50")
    private Double minGpa;
    @NotNull(message = "requirement is required")
    private String requirements;
    @Transient // Not stored in the database — only used for Elasticsearch or REST
    private List<String> requiredQualifications;

    @Column(name = "required_qualifications")
    private String requiredQualificationsRaw;

    private LocalDate postedDate;
    @OneToMany(mappedBy = "jobOpening", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applicationList = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @JsonIgnoreProperties({"applications", "authorities", "password"})
    private User createdBy;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(LocalDate postedDate) {
        this.postedDate = postedDate;
    }

    public List<Application> getApplicationList() {
        return applicationList;
    }

    public void setApplicationList(List<Application> applicationList) {
        this.applicationList = applicationList;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getMinExperience() {
        return minExperience;
    }

    public void setMinExperience(Integer minExperience) {
        this.minExperience = minExperience;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Double getMinGpa() {
        return minGpa;
    }

    public void setMinGpa(Double minGpa) {
        this.minGpa = minGpa;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public @NotNull(message = "minAge is required") Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(@NotNull(message = "minAge is required") Integer minAge) {
        this.minAge = minAge;
    }

    public @NotNull(message = "requirement is required") String getRequirements() {
        return requirements;
    }

    public void setRequirements(@NotNull(message = "requirement is required") String requirements) {
        this.requirements = requirements;
    }


    public List<String> getRequiredQualifications() {
        if (requiredQualifications == null && requiredQualificationsRaw != null) {
            return Arrays.stream(requiredQualificationsRaw.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
        }
        return requiredQualifications;
    }

    public void setRequiredQualifications(List<String> qualifications) {
        this.requiredQualifications = qualifications;
        this.requiredQualificationsRaw = qualifications != null
                ? String.join(",", qualifications)
                : null;
    }

    public String getRequiredQualificationsRaw() {
        return requiredQualificationsRaw;
    }

    public void setRequiredQualificationsRaw(String raw) {
        this.requiredQualificationsRaw = raw;
        this.requiredQualifications = raw != null
                ? Arrays.stream(raw.split(",")).map(String::trim).collect(Collectors.toList())
                : null;
    }


}
