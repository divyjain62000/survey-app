package com.survey.service.survey.impl;

import com.survey.domain.survey.Survey;
import com.survey.enums.error.ErrorFor;
import com.survey.enums.error.survey.SurveyErr;
import com.survey.exception.SurveyAppException;
import com.survey.repository.survey.SurveyRepository;
import com.survey.service.auth.security.SecurityUtils;
import com.survey.service.survey.SurveyService;
import com.survey.service.survey.dto.SurveyDTO;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SecurityUtils securityUtils;

    public void save(SurveyDTO surveyDTO) throws SurveyAppException
    {
        SurveyAppException surveyAppException=validate(surveyDTO);
        if(surveyAppException==null) {
            ModelMapper mapper = new ModelMapper();
            Survey survey = mapper.map(surveyDTO, Survey.class);
            survey.setCreatedBy(securityUtils.getUser());
            this.surveyRepository.save(survey);
        }
        else {
            if(surveyAppException.getExceptions().size()>0) throw surveyAppException;
        }
    }

    private SurveyAppException validate(SurveyDTO surveyDTO) throws SurveyAppException{
        SurveyAppException surveyAppException=new SurveyAppException();
        if(surveyDTO==null) {
            surveyAppException.addException(ErrorFor.SURVEY_DETAILS_ERR.name(), SurveyErr.SURVEY_DETAILS_REQUIRED.getSurveyErr());
            throw surveyAppException;
        }
        if(surveyDTO.getName()==null || surveyDTO.getName().length()==0) {
            surveyAppException.addException(ErrorFor.SURVEY_NAME_ERR.name(), SurveyErr.SURVEY_NAME_REQUIRED.getSurveyErr());
        }
        if(surveyDTO.getDescription()==null || surveyDTO.getDescription().length()==0) {
            surveyAppException.addException(ErrorFor.SURVEY_DESCRIPTION_ERR.name(), SurveyErr.SURVEY_DESCRIPTION_REQUIRED.getSurveyErr());
        }
        if(surveyDTO.getLastDateToFill()==null) {
            surveyAppException.addException(ErrorFor.LAST_DATE_TO_FILL_SURVEY_ERR.name(),SurveyErr.LAST_DATE_TO_FILL_SURVEY_REQUIRED.getSurveyErr());
        }else {
            LocalDateTime NOW = LocalDateTime.now();
            if(surveyDTO.getLastDateToFill().isBefore(NOW)) {
                surveyAppException.addException(ErrorFor.LAST_DATE_TO_FILL_SURVEY_ERR.name(),SurveyErr.INVALID_LAST_DATE_TO_FILL_SURVEY.getSurveyErr());
            }
        }
        if(surveyAppException.getExceptions().size()>0) return surveyAppException;
        return null;
    }
}
