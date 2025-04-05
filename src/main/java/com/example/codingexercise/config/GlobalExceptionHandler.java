package com.example.codingexercise.config;

import com.example.codingexercise.exception.CodingExerciseRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CodingExerciseRuntimeException.class})
    protected ProblemDetail handleException(CodingExerciseRuntimeException e) {
        return ProblemDetail.forStatusAndDetail(e.getErrorCode().getHttpStatus(), e.getErrorCode().getErrorMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    protected ProblemDetail handleException(Exception e) {
        log.error("Internal Server Error!", e);
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), e.getMessage());
    }


}
