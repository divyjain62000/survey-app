package com.survey.web.rest.domain.survey;

import com.survey.exception.SurveyAppException;
import com.survey.response.ActionResponse;
import com.survey.service.survey.SurveyService;
import com.survey.service.survey.dto.SurveyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/survey")
public class SurveyResource {

    @Autowired
    private SurveyService surveyService;

    @PostMapping("/create")
    public ResponseEntity<ActionResponse> createSurvey(@RequestBody SurveyDTO surveyDTO) {
        ActionResponse actionResponse=new ActionResponse();
        try{
            surveyService.save(surveyDTO);
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
            return ResponseEntity.internalServerError().body(actionResponse);
        }
    }
}
