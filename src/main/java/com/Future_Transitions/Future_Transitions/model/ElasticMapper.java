//package com.Future_Transitions.Future_Transitions.model;
//
//import java.util.Optional;
//
//public final class ElasticMapper {
//
//    private ElasticMapper() {
//
//    }
//
//    public static JobOpeningElastic toElastic(JobOpening job) {
//        return JobOpeningElastic.builder()
//                .id(String.valueOf(job.getId()))
//                .title(job.getTitle())
//                .description(job.getDescription())
//                .maxAge(job.getMaxAge())
//                .minAge(job.getMinAge())
//                .minExperience(job.getMinExperience())
//                .minGpa(job.getMinGpa())
//                .requiredQualifications(job.getRequiredQualifications())
//                .postedDate(job.getPostedDate())
//                .createdBy(Optional.ofNullable(job.getCreatedBy())
//                        .map(User::getUsername)
//                        .orElse(null))
//                .applicationCount(Optional.ofNullable(job.getApplicationList())
//                        .map(list -> list.size())
//                        .orElse(0))
//                .build();
//    }
//
//    public static ApplicationElastic toElastic(Application app) {
//        return ApplicationElastic.builder()
//                .id(String.valueOf(app.getId()))
//                .jobId(Optional.ofNullable(app.getJobId())
//                        .map(String::valueOf)
//                        .orElse(null))
//                .applicationName(app.getApplicationName())
//                .province(Optional.ofNullable(app.getProvince())
//                        .map(Enum::name)
//                        .orElse(null))
//                .status(Optional.ofNullable(app.getStatus())
//                        .map(Enum::name)
//                        .orElse(null))
//                .cvPath(app.getCvPath())
//                .idDocumentPath(app.getIdDocumentPath())
//                .coverLetterPath(app.getCoverLetterPath())
//                .appliedDate(app.getAppliedDate())
//                .maxAge(app.getMaxAge())
//                .minExperience(app.getMinExperience())
//                .minGpa(app.getMinGpa())
//                .candidateQualifications(app.getCandidateQualifications())
//                .build();
//    }
//}
