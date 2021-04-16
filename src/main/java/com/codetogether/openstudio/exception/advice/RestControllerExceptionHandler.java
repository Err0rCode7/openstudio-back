package com.codetogether.openstudio.exception.advice;

import com.codetogether.openstudio.dto.CommonResponseDto;
import com.codetogether.openstudio.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice(annotations = RestController.class)
public class RestControllerExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class, NoSuchElementException.class})
    public ResponseEntity<ErrorResponseDto> errorHandler(RuntimeException e) {

        ErrorResponseDto responseDto = new ErrorResponseDto(false, e.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.resolve(500));
    }
}
