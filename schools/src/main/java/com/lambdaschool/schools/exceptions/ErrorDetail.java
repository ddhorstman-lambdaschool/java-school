package com.lambdaschool.schools.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

public class ErrorDetail {
    private String message;
    private int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    private List<String> details;
    private Date timestamp = new Date();

//    @JsonIgnore
    private String exceptionType = "java.lang.Exception";


    public ErrorDetail(){}

    public ErrorDetail(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }

    public ErrorDetail(String message, int status, List<String> details) {
        this.message = message;
        this.status = status;
        this.details = details;
    }

    public ErrorDetail(String message, int status, List<String> details, String exceptionType) {
        this.message = message;
        this.status = status;
        this.details = details;
        this.exceptionType = exceptionType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }
}
