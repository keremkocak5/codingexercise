package com.example.codingexercise.exception;

import com.example.codingexercise.enums.ErrorCode;
import lombok.Getter;

public class CodingExerciseRuntimeException extends RuntimeException {

    @Getter
    private final ErrorCode errorCode;

    public CodingExerciseRuntimeException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
