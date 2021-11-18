package com.survey.repository.survey.response;

import com.survey.domain.survey.response.SurveyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Integer> {
    List<SurveyResponse> findBySurveyIdAndUserId(Integer surveyId, Integer userId);
    Page<SurveyResponse> findAllBySurveyIdOrderByUserId(Integer surveyId, Pageable pageable);
}
