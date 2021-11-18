package com.survey.service.survey.dto;

import com.survey.service.dto.DataTransferObject;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
public class SurveyDTO extends DataTransferObject {

    private Integer id;
    private String name;
    private String description;
    private LocalDateTime lastDateToFill;
    private Boolean isResponseEditable;

}
