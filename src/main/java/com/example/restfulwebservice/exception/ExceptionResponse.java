package com.example.restfulwebservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

// 예외처리를 위한 객체
@Data
@AllArgsConstructor
public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private String details;
}
