package com.survey.service.question.dto;

import com.survey.service.dto.DataTransferObject;
import lombok.Data;

@Data
public class QuestionDTO extends DataTransferObject {

    private Integer id;
    private String que;
    private Integer surveyId;

}
