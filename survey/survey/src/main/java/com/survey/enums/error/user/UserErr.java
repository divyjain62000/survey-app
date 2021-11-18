package com.survey.enums.error.user;

public enum UserErr {
    USER_ID_REQURIED("User id requried"),
    INVALID_USER_ID("Invalid user");

    private String userErr;
    UserErr(String userErr) { this.userErr=userErr; }

    public String getUserErr() {
        return userErr;
    }
}
