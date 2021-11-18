package com.survey.enums.error.survey;

public enum SurveyResponseErr {

    SURVEY_RESPONSE_DETAILS_REQUIRED("Survey reponse details required"),
    SURVEY_QUESTIONS("You have not filled any questions"),
    ALL_SURVEY_QUESTIONS_REQUIRED("You have not fill all questions of survey"),
    ALREADY_FILLED("Your response is already submitted you can not change your response"),
    ANS_REQUIRED("Answer required"),
    SURVEY_CLOSED("No longer taking response, Survey closed on"),
    NOT_ALLOW_TO_SEE_RESPONSE("You are not allowed to see response");

    private String surveyResponseErr;
    SurveyResponseErr(String surveyResponseErr) { this.surveyResponseErr=surveyResponseErr; }

    public String getSurveyResponseErr() {
        return surveyResponseErr;
    }
}
