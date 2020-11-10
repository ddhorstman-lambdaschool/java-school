package com.lambdaschool.schools.handlers;

import com.lambdaschool.schools.exceptions.ErrorDetail;
import com.lambdaschool.schools.exceptions.ResourceFoundException;
import com.lambdaschool.schools.exceptions.ResourceNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// https://howtodoinjava.com/spring-boot2/spring-rest-request-validation/

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    public RestExceptionHandler() {
        super();
    }

    public ResponseEntity<Object> handleAllExceptions(Exception ex, HttpStatus status) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorDetail errorDetail = new ErrorDetail(
                status.getReasonPhrase(),
                status.value(),
                details,
                ex.getClass().getName());

        return new ResponseEntity<>(errorDetail, null, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncategorizedExceptions(Exception ex) {
        return handleAllExceptions(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException rnfe) {
        return handleAllExceptions(rnfe, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceFoundException.class)
    public ResponseEntity<Object> handleResourceFoundException(ResourceFoundException rfe){
        return handleAllExceptions(rfe, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorDetail error = new ErrorDetail("Invalid Request Body", HttpStatus.BAD_REQUEST.value(), details, ex.getClass().getName());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return this.handleAllExceptions(ex, status);
    }
}
