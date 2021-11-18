package com.survey.service.survey;

import com.survey.exception.SurveyAppException;
import com.survey.service.survey.dto.SurveyDTO;

public interface SurveyService {
    void save(SurveyDTO surveyDTO) throws SurveyAppException;
}
