package com.doublep.vrssapi.advisor.exception;

/**
 */
public class CustomFileReadException extends RuntimeException {

    private static final String DEFAULT_MSG = "CSV 파일 읽기에 실패했습니다.";

    public CustomFileReadException() {
        super(DEFAULT_MSG);
    }

    public CustomFileReadException(String msg) {
        super(msg);
    }

    public CustomFileReadException(String msg, Throwable t) {
        super(msg, t);
    }
}
