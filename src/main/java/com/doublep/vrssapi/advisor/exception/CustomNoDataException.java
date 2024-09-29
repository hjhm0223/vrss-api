package com.doublep.vrssapi.advisor.exception;

/**
 * Custom No Data Exception
 * return 400 status code and error message.
 */
public class CustomNoDataException extends RuntimeException {

    private static final String DEFAULT_MSG = "존재하지 않는 데이터입니다.";

    public CustomNoDataException() {
        super(DEFAULT_MSG);
    }

    public CustomNoDataException(String msg) {
        super(msg);
    }

    public CustomNoDataException(String msg, Throwable t) {
        super(msg, t);
    }

}
