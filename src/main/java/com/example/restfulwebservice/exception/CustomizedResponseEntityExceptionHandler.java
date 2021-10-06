package com.example.restfulwebservice.exception;

import com.example.restfulwebservice.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

//@RestController
@ControllerAdvice // 모든 컨트롤러 실행 시 이 컨트롤러가 미리 실행됨 (모든 예외 상황에 반드시 이 핸들러를 거친다)
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // 모든 공통적인 예외 해당
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {

        // 우리가 만든 예외객체를 생성
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        // ResponseEntity에 예외객체와 상태코드 보내준다.
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 유저의 값이 없을 때 발생하는 예외 해당
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundExceptions(Exception ex, WebRequest request) {

        // 우리가 만든 예외객체를 생성
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        // ResponseEntity에 예외객체와 상태코드 보내준다.
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    // valid 예외처리
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        // 우리가 만든 예외 객체를 생성
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), "Validation Failed", ex.getBindingResult().toString());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
