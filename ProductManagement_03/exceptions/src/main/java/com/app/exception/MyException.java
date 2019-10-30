package com.app.exception;

public class MyException extends RuntimeException {
    private final String exceptionMessage;

    public MyException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
