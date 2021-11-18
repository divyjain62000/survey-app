package com.survey.service.question.impl;

import com.survey.domain.question.Question;
import com.survey.domain.survey.Survey;
import com.survey.enums.error.ErrorFor;
import com.survey.enums.error.question.QuestionErr;
import com.survey.enums.error.survey.SurveyErr;
import com.survey.exception.SurveyAppException;
import com.survey.repository.question.QuestionRepository;
import com.survey.repository.survey.SurveyRepository;
import com.survey.service.question.QuestionService;
import com.survey.service.question.dto.QuestionDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    @Override
    public void save(QuestionDTO questionDTO) throws SurveyAppException {
        SurveyAppException surveyAppException=validate(questionDTO);
        if(surveyAppException==null) {
            ModelMapper mapper = new ModelMapper();
            questionDTO.setId(0);
            Question question = mapper.map(questionDTO, Question.class);
            System.out.println("Question:"+question);
           this.questionRepository.save(question);
        }else {
            if(surveyAppException.getExceptions().size()>0) throw surveyAppException;
        }
    }

    private SurveyAppException validate(QuestionDTO questionDTO) throws SurveyAppException{
        SurveyAppException surveyAppException=new SurveyAppException();
        if(questionDTO==null) {
            surveyAppException.addException(ErrorFor.SURVEY_DETAILS_ERR.name(), SurveyErr.SURVEY_DETAILS_REQUIRED.getSurveyErr());
            throw surveyAppException;
        }
        if(questionDTO.getQue()==null || questionDTO.getQue().length()==0) {
            surveyAppException.addException(ErrorFor.QUE_ERR.name(), QuestionErr.QUE_REQUIRED.getQueErr());
        }
        if(questionDTO.getSurveyId()==null) {
            surveyAppException.addException(ErrorFor.SURVEY_ID_ERR.name(),SurveyErr.SURVEY_ID_REQUIRED.getSurveyErr());
        }else {
            log.debug("Try to retrieve survey with specific id");
            Optional<Survey> survey=this.surveyRepository.findById(questionDTO.getSurveyId());
            if(!survey.isPresent()) {
                surveyAppException.addException(ErrorFor.SURVEY_ID_ERR.name(), SurveyErr.INVALID_SURVEY_ID.getSurveyErr());
            }
        }
        if(surveyAppException.getExceptions().size()>0) return surveyAppException;
        return null;
    }



}
