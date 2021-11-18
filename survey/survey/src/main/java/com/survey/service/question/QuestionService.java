package com.survey.service.question;

import com.survey.exception.SurveyAppException;
import com.survey.service.question.dto.QuestionDTO;


public interface QuestionService {
    void save(QuestionDTO questionDTO) throws SurveyAppException;
}
