package com.survey.service.survey.response.impl;

import com.survey.domain.question.Question;
import com.survey.domain.survey.response.SurveyResponse;
import com.survey.domain.survey.response.SurveyResponseTrack;
import com.survey.domain.survey.Survey;
import com.survey.domain.user.User;
import com.survey.enums.error.ErrorFor;
import com.survey.enums.error.survey.SurveyErr;
import com.survey.enums.error.survey.SurveyResponseErr;
import com.survey.exception.SurveyAppException;
import com.survey.repository.question.QuestionRepository;
import com.survey.repository.survey.SurveyRepository;
import com.survey.repository.survey.response.SurveyResponseRepository;
import com.survey.repository.survey.response.SurveyResponseTrackRepository;
import com.survey.repository.user.UserRepository;
import com.survey.service.auth.security.SecurityUtils;
import com.survey.service.survey.response.SurveyResponseService;
import com.survey.service.survey.response.dto.SurveyResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class SurveyResponseServiceImpl implements SurveyResponseService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyResponseRepository surveyResponseRepository;

    @Autowired
    private SurveyResponseTrackRepository surveyResponseTrackRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityUtils securityUtils;

    @Override
    public void save(SurveyResponseDTO surveyResponseDTO) throws SurveyAppException {
        Integer surveyId=surveyResponseDTO.getSurveyId();

        SurveyAppException surveyAppException=validate(surveyId,surveyResponseDTO);
        if(surveyAppException==null) {
            User user=securityUtils.getUser();
            Optional<Survey> survey=this.surveyRepository.findById(surveyResponseDTO.getSurveyId());
            ModelMapper mapper=new ModelMapper();
            Map<Integer,Boolean> questionAnsPair=surveyResponseDTO.getQuestionAnsPair();
            for (Map.Entry<Integer, Boolean> entry : questionAnsPair.entrySet()) {
                if(entry!=null) {
                    SurveyResponse surveyResponse = new SurveyResponse();
                    Optional<Question> question = this.questionRepository.findById(entry.getKey());
                    if(question.isPresent() && survey.isPresent()) {
                        surveyResponse.setQuestion(question.get());
                        surveyResponse.setSurvey(survey.get());
                        surveyResponse.setUser(user);
                        surveyResponse.setAns(entry.getValue());
                        log.debug("QuNo. {},Ans {}", entry.getKey(), entry.getValue());
                        this.surveyResponseRepository.save(surveyResponse);
                    }
                }
            }
            SurveyResponseTrack surveyResponseTrack=new SurveyResponseTrack();
            surveyResponseTrack.setSurvey(survey.get());
            surveyResponseTrack.setUser(user);
            this.surveyResponseTrackRepository.save(surveyResponseTrack);
        }
        else {
            if(surveyAppException.getExceptions().size()>0) throw surveyAppException;
        }
    }


    @Override
    public Map<Integer,List<Pair<String,Boolean>>> findAllBySurveyId(Integer surveyId, Pageable pageable) throws SurveyAppException {
        Optional<Survey> survey=this.surveyRepository.findById(surveyId);
        if(survey.isPresent() && survey.get()==null) {
            SurveyAppException surveyAppException=new SurveyAppException();
            surveyAppException.addException(ErrorFor.SURVEY_RESPONSE_VIEW_ERR.name(),SurveyResponseErr.NOT_ALLOW_TO_SEE_RESPONSE.getSurveyResponseErr());
            throw  surveyAppException;
        }
        if(survey.isPresent() ) {
            if (survey.get().getCreatedBy()!=null) {
                if (survey.get().getCreatedBy().getId() != securityUtils.getUser().getId()) {
                    SurveyAppException surveyAppException = new SurveyAppException();
                    surveyAppException.addException(ErrorFor.SURVEY_RESPONSE_VIEW_ERR.name(), SurveyResponseErr.NOT_ALLOW_TO_SEE_RESPONSE.getSurveyResponseErr());
                    throw surveyAppException;
                }
            }
        }

        Page<SurveyResponse> surveyResponsePage=this.surveyResponseRepository.findAllBySurveyIdOrderByUserId(surveyId,pageable);
        List<SurveyResponse> surveyResponseList=surveyResponsePage.getContent();
        Map<Integer,List<Pair<String,Boolean>>> surveyResult=new HashMap<>();
        surveyResponseList.forEach((surveyResponse)->{
            Integer userId=surveyResponse.getUser().getId();
            if(surveyResult.containsKey(userId)==false) {
                List<Pair<String,Boolean>> questionAnsList=new LinkedList<>();
                Pair<String,Boolean> questionAns=Pair.of(surveyResponse.getQuestion().getQue(),surveyResponse.getAns());
                questionAnsList.add(questionAns);
                surveyResult.put(userId,questionAnsList);
            }else {
                List<Pair<String,Boolean>> questionAnsList=surveyResult.get(userId);
                if(surveyResponse!=null && surveyResponse.getQuestion()!=null) {
                    Pair<String,Boolean> questionAns=Pair.of(surveyResponse.getQuestion().getQue(),surveyResponse.getAns());
                    questionAnsList.add(questionAns);
                }

                surveyResult.put(userId,questionAnsList);
            }
        });
        return surveyResult;
    }




    private SurveyAppException validate(Integer surveyId,SurveyResponseDTO surveyResponseDTO) {
        SurveyAppException surveyAppException=new SurveyAppException();
        if(surveyResponseDTO==null) {
            surveyAppException.addException(ErrorFor.SURVEY_RESPONSE_DETAILS_ERR.name(), SurveyResponseErr.SURVEY_RESPONSE_DETAILS_REQUIRED.getSurveyResponseErr());
            return surveyAppException;
        }
        Optional<Survey> survey=this.surveyRepository.findById(surveyId);
        if(survey.isPresent()) {
            LocalDateTime lastDateToFill=survey.get().getLastDateToFill();
            if(lastDateToFill.isBefore(LocalDateTime.now())) {
                surveyAppException.addException(ErrorFor.LAST_DATE_TO_FILL_SURVEY_ERR.name(),SurveyResponseErr.SURVEY_CLOSED.getSurveyResponseErr()+lastDateToFill);
            }

            if(survey.get().isResponseEditable()==false) {
                List<SurveyResponseTrack> surveyResponseTrackList=this.surveyResponseTrackRepository.findBySurveyIdAndUserId(surveyId,securityUtils.getUser().getId());
               log.debug("surveyResponseTrackList: {}",surveyResponseTrackList);
                log.debug("surveyResponseTrackList size: {}",surveyResponseTrackList.size());
                if(surveyResponseTrackList!=null && surveyResponseTrackList.size()>0) {
                    surveyAppException.addException(ErrorFor.SURVEY_FILLED_ERR.name(),SurveyResponseErr.ALREADY_FILLED.getSurveyResponseErr());
                    return surveyAppException;
                }
            }
        }else {
            surveyAppException.addException(ErrorFor.SURVEY_ID_ERR.name(), SurveyErr.INVALID_SURVEY_ID.getSurveyErr());
            return surveyAppException;
        }
        if(surveyResponseDTO.getQuestionAnsPair()==null || surveyResponseDTO.getQuestionAnsPair().size()<=0) {
            surveyAppException.addException(ErrorFor.SURVEY_QUESTION_ERR.name(), SurveyResponseErr.SURVEY_RESPONSE_DETAILS_REQUIRED.getSurveyResponseErr());
        }else {
            List<Question> questionList = this.questionRepository.findAllBySurveyId(surveyId);
            log.debug("questionList: {}",questionList.size());
            log.debug("questionAnsPair: {}", surveyResponseDTO.getQuestionAnsPair().size());
            if(surveyResponseDTO.getQuestionAnsPair().size()!=questionList.size()) {
                surveyAppException.addException(ErrorFor.SURVEY_QUESTION_ERR.name(), SurveyResponseErr.ALL_SURVEY_QUESTIONS_REQUIRED.getSurveyResponseErr());
            }else {
                int i=0;
                for (Map.Entry<Integer, Boolean> entry : surveyResponseDTO.getQuestionAnsPair().entrySet()) {
                    SurveyAppException saException = new SurveyAppException();
                    if(entry.getValue()==null) saException.addException(ErrorFor.ANS_ERR.name(), SurveyResponseErr.ANS_REQUIRED.getSurveyResponseErr()+" for Question No. "+i);
                    if (saException.getExceptions().size() > 0) return saException;
                    i++;
                }
            }
        }
        if(surveyAppException.getExceptions().size()>0) return surveyAppException;
        return null;
    }




}
