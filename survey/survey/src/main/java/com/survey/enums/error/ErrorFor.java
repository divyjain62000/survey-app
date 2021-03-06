package com.survey.enums.error;

public enum ErrorFor {
    SURVEY_DETAILS_ERR("SURVEY_DETAILS_ERR"),
    SURVEY_ID_ERR("SURVEY_ID_ERR"),
    SURVEY_NAME_ERR("SURVEY_NAME_ERR"),
    SURVEY_DESCRIPTION_ERR("SURVEY_DESCRIPTION_ERR"),
    LAST_DATE_TO_FILL_SURVEY_ERR("LAST_DATE_TO_FILL_SURVEY_ERR"),
    QUESTION_DETAILS_ERR("QUESTION_DETAILS_ERR"),
    QUESTION_ID_ERR("QUESTION_ID_ERR"),
    QUE_ERR("QUE_ERR"),
    ANS_ERR("ANS_ERR"),
    SURVEY_RESPONSE_DETAILS_ERR("SURVEY_RESPONSE_DETAILS_ERR"),
    SURVEY_QUESTION_ERR("SURVEY_QUESTION_ERR"),
    SURVEY_FILLED_ERR("SURVEY_FILLED_ERR"),
    SURVEY_RESPONSE_VIEW_ERR("SURVEY_RESPONSE_VIEW_ERR");

    private String errorFor;
    ErrorFor(String errorFor) { this.errorFor =  errorFor; }

    public String getErrorFor() {
        return errorFor;
    }
}
