package com.survey.web.rest.domain.question;

import com.survey.exception.SurveyAppException;
import com.survey.response.ActionResponse;
import com.survey.service.question.QuestionService;
import com.survey.service.question.dto.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/question")
public class QuestionResource {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/add")
    public ResponseEntity<ActionResponse> addQuestion(@RequestBody QuestionDTO questionDTO) {
        ActionResponse actionResponse=new ActionResponse();
        try{
            questionService.save(questionDTO);
            actionResponse.setSuccessful(true);
            actionResponse.setResult(null);
            actionResponse.setException(false);
            return ResponseEntity.ok().body(actionResponse);
        }catch (SurveyAppException surveyAppException) {
            actionResponse.setSuccessful(false);
            actionResponse.setResult(surveyAppException.getExceptions());
            actionResponse.setException(true);
            return ResponseEntity.ok().body(actionResponse);
        }
        catch (Exception exception) {
            actionResponse.setSuccessful(false);
            actionResponse.setResult(exception);
            actionResponse.setException(true);
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body(actionResponse);
        }
    }

}
