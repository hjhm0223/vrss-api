package com.doublep.kcgsasystem.advisor.exception;

/**
 * Custom BadRequest Exception
 * return 400 status code and error message.
 */
public class CustomBadRequestException extends RuntimeException {

    private static final String DEFAULT_MSG = "잘못된 요청입니다.";

    public CustomBadRequestException() {
        super(DEFAULT_MSG);
    }

    public CustomBadRequestException(String msg) {
        super(msg);
    }

    public CustomBadRequestException(String msg, Throwable t) {
        super(msg, t);
    }
}
