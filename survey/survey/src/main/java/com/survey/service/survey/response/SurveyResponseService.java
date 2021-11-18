package com.survey.service.survey.response;

import com.survey.exception.SurveyAppException;
import com.survey.service.survey.response.dto.SurveyResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public interface SurveyResponseService {
    void save(SurveyResponseDTO surveyResponseDTODTO) throws SurveyAppException;
    Map<Integer, List<Pair<String,Boolean>>> findAllBySurveyId(Integer SurveyId, Pageable pageable) throws SurveyAppException;
}
