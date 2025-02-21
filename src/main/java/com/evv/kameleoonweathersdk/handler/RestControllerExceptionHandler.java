package com.evv.kameleoonweathersdk.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ProblemDetail> handleHttpClientErrorException(HttpClientErrorException e) {

        log.warn(e.getMessage(), e);

        HttpStatus httpStatus = HttpStatus.valueOf(e.getStatusCode().value());

        ProblemDetail problemDetail = createProblemDetail(httpStatus, e.getMessage(), e.getStatusText());
        return ResponseEntity.status(e.getStatusCode()).body(problemDetail);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.warn(e.getMessage(), e);

        String messages = e.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("; "));

        HttpStatus httpStatus = HttpStatus.valueOf(e.getStatusCode().value());

        ProblemDetail problemDetail = createProblemDetail(httpStatus, messages, "Validation Error");
        return ResponseEntity.status(e.getStatusCode()).body(problemDetail);
    }


    private ProblemDetail createProblemDetail(HttpStatus status, String detail, String title) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setProperty("timestamp", Instant.now());
        if (title != null) {
            problemDetail.setTitle(title);
        } else {
            problemDetail.setTitle(detail);
        }
        return problemDetail;
    }
}
