package com.doublep.vrssapi.advisor;

import com.doublep.vrssapi.advisor.exception.CustomBadRequestException;
import com.doublep.vrssapi.advisor.exception.CustomNoDataException;
import com.doublep.vrssapi.advisor.exception.CustomSvmpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Exception Handler
 *
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.doublep.vrssapi.controller")
public class ExceptionAdvice {

    @ExceptionHandler({Exception.class, RuntimeException.class, CustomSvmpException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ResponseEntity<?> internalServerErrorException(Exception exception) {
        exception.printStackTrace();
        log.error("InternalServerException. message={}", exception.getLocalizedMessage());
        return ResponseEntity.internalServerError().body(exception.getLocalizedMessage());
    }

    @ExceptionHandler({CustomNoDataException.class, CustomBadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<?> badRequestException(RuntimeException exception) {
        exception.printStackTrace();
        log.error("BadRequestException. message={}", exception.getLocalizedMessage());
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<?> bindException(BindException exception) {
        exception.printStackTrace();
        StringBuilder stringBuilder = new StringBuilder("BindException. message=");
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            String message = error.getDefaultMessage();
            stringBuilder.append(message).append("\n");
            log.error(message);
        });
        return ResponseEntity.badRequest().body(stringBuilder.toString());
    }
}
