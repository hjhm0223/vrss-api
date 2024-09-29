package com.doublep.vrssapi.advisor.exception;

/**
 * Custom Svmp Custom Exception
 * return 500 status code and error message.
 */
public class CustomSvmpException extends RuntimeException {

    private static final String DEFAULT_MSG = "SVMP 시스템 오류입니다.";

    public CustomSvmpException() {
        super(DEFAULT_MSG);
    }

    public CustomSvmpException(String msg) {
        super(msg);
    }

    public CustomSvmpException(String msg, Throwable t) {
        super(msg, t);
    }
}
