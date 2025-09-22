//package com.Future_Transitions.Future_Transitions.service;
//
//import com.Future_Transitions.Future_Transitions.model.JobOpeningElastic;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import com.Future_Transitions.Future_Transitions.model.JobOpeningElastic;
//import lombok.RequiredArgsConstructor;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//
//@Service
//@RequiredArgsConstructor
//public class ElasticSearchFilter {
//
//
//    private final ElasticsearchRestTemplate elasticsearchTemplate;
//
//    public List<JobOpeningElastic> searchJobs(String title, String createdBy, Integer maxAge,
//                                              Integer minExperience, Double minGpa, String qualification) {
//
//        BoolQueryBuilder query = QueryBuilders.boolQuery();
//
//        if (title != null) query.must(QueryBuilders.matchQuery("title", title));
//        if (createdBy != null) query.must(QueryBuilders.matchQuery("createdBy", createdBy));
//        if (maxAge != null) query.must(QueryBuilders.rangeQuery("maxAge").gte(maxAge));
//        if (minExperience != null) query.must(QueryBuilders.rangeQuery("minExperience").gte(minExperience));
//        if (minGpa != null) query.must(QueryBuilders.rangeQuery("minGpa").gte(minGpa));
//        if (qualification != null) query.must(QueryBuilders.matchQuery("requiredQualifications", qualification));
//
//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(query)
//                .build();
//
//        return elasticsearchTemplate.queryForList(searchQuery, JobOpeningElastic.class);
//    }
//}
