package com.sprint.mission.discodeit.exception;

import java.time.Instant;
import java.util.Map;
import java.util.NoSuchElementException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DiscodeitException.class)
  public ResponseEntity<ErrorResponse> handleDiscodeitException(DiscodeitException e) {
    ErrorCode errorCode = e.getErrorCode();

    ErrorResponse response = ErrorResponse.builder()
            .timestamp(Instant.now())
            .code(errorCode.getCode())
            .message(errorCode.getMessage())
            .details(e.getDetails())
            .exceptionType(e.getClass().getSimpleName())
            .status(errorCode.getStatus().value())
            .build();

    log.warn("예외 발생 {} : {}", response.getExceptionType(), response.getMessage());

    return ResponseEntity
            .status(errorCode.getStatus())
            .body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;

    ErrorResponse response = ErrorResponse.builder()
            .timestamp(Instant.now())
            .code(errorCode.getCode())
            .message(errorCode.getMessage())
            .details(Map.of("reason", e.getMessage() != null ? e.getMessage() : "No message available"))
            .exceptionType(e.getClass().getSimpleName())
            .status(errorCode.getStatus().value())
            .build();

    log.error("서버 내부 오류 발생", e);

    return ResponseEntity
            .status(errorCode.getStatus())
            .body(response);
  }
}
