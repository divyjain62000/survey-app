package com.survey.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ActionResponse {
    private boolean successful;
    private boolean isException;
    private Object result;
}
