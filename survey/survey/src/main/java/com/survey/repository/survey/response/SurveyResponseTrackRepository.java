package com.survey.repository.survey.response;

import com.survey.domain.survey.response.SurveyResponseTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyResponseTrackRepository extends JpaRepository<SurveyResponseTrack, Integer> {

    List<SurveyResponseTrack> findBySurveyIdAndUserId(Integer surveyId,Integer userId);
}
