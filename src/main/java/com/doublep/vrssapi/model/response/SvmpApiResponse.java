package com.doublep.vrssapi.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SvmpApiResponse<T> {

    private Integer code;
    private Boolean error;
    private String errorMessage;
    private String message;
    private LocalDateTime timestamp;
    private T response;
}
