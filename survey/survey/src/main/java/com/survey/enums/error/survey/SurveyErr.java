package com.survey.enums.error.survey;

public enum SurveyErr {
    SURVEY_DETAILS_REQUIRED("Survey details required"),
    SURVEY_ID_REQUIRED("Please select the survey"),
    SURVEY_NAME_REQUIRED("Survey name required"),
    SURVEY_DESCRIPTION_REQUIRED("Survey description required"),
    INVALID_SURVEY_ID("Survey not exists"),
    LAST_DATE_TO_FILL_SURVEY_REQUIRED("Last date to fill survey required"),
    INVALID_LAST_DATE_TO_FILL_SURVEY("Invalid date");

    private String surveyErr;
    SurveyErr(String surveyErr) { this.surveyErr=surveyErr; }

    public String getSurveyErr() {
        return surveyErr;
    }
}
