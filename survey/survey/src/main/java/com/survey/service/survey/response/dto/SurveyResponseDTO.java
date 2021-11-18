package com.survey.service.survey.response.dto;

import com.survey.domain.question.Question;
import com.survey.service.dto.DataTransferObject;
import com.survey.service.question.dto.QuestionDTO;
import lombok.Data;
import org.springframework.data.util.Pair;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class SurveyResponseDTO extends DataTransferObject {
    Map<Integer,Boolean> questionAnsPair;
    Integer surveyId;
}
