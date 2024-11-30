package com.doublep.vrssapi.advisor.exception;

/**
 */
public class CustomJsonProcessingException extends RuntimeException {

    private static final String DEFAULT_MSG = "JsonProcessingException이 발생했습니다.";

    public CustomJsonProcessingException() {
        super(DEFAULT_MSG);
    }

    public CustomJsonProcessingException(String msg) {
        super(msg);
    }

    public CustomJsonProcessingException(String msg, Throwable t) {
        super(msg, t);
    }
}
