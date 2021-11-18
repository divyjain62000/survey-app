package com.survey.repository.survey.response;

import com.survey.domain.survey.response.SurveyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Integer> {

    Page<SurveyResponse> findAllBySurveyIdOrderByUserId(Integer surveyId, Pageable pageable);
}
