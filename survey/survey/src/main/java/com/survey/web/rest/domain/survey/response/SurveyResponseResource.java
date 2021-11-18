package com.survey.web.rest.domain.survey.response;


import com.survey.exception.SurveyAppException;
import com.survey.response.ActionResponse;
import com.survey.service.survey.response.SurveyResponseService;
import com.survey.service.survey.response.dto.SurveyResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/survey-response")
@Slf4j
public class SurveyResponseResource {

    @Autowired
    private SurveyResponseService surveyResponseService;

    @PostMapping("/add")
    public ResponseEntity<ActionResponse> addResponse(@RequestBody SurveyResponseDTO surveyResponseDTO) {
        ActionResponse actionResponse=new ActionResponse();
        try{
            this.surveyResponseService.save(surveyResponseDTO);
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

    @GetMapping("/{surveyId}")
    public ResponseEntity<?> getResponse(@PathVariable(name="surveyId") Integer surveyId, Pageable pageable) {
        ActionResponse actionResponse=new ActionResponse();
        try{
            Map<Integer,List<Pair<String,Boolean>>> surveyResult=this.surveyResponseService.findAllBySurveyId(surveyId,pageable);
            return ResponseEntity.ok().body(surveyResult);
        }catch (SurveyAppException surveyAppException) {
            actionResponse.setSuccessful(false);
            actionResponse.setResult(surveyAppException.getExceptions());
            actionResponse.setException(true);
            return ResponseEntity.internalServerError().body(actionResponse);
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
