package com.survey.service.auth.dto;

import com.survey.service.dto.DataTransferObject;
import lombok.Data;

import javax.persistence.Column;

@Data
public class UserDTO extends DataTransferObject {

    private Integer id;
    private String username;
    private String password;

}
