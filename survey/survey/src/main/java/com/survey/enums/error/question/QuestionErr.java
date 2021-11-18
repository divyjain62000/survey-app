package com.survey.enums.error.question;

import com.survey.domain.question.Question;

public enum QuestionErr {

    QUE_REQUIRED("Question required");

    private String queErr;
    QuestionErr(String queErr) { this.queErr=queErr; }
    public String getQueErr() {
        return queErr;
    }
}
