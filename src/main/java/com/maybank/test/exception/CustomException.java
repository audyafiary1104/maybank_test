package com.maybank.test.exception;

public class CustomException extends RuntimeException {

    private String errorCode;
    private String errorMessage;

    public CustomException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
