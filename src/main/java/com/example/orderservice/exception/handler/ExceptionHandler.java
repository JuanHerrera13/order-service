package com.example.orderservice.exception.handler;

import com.example.orderservice.exception.NotFoundException;
import com.example.orderservice.exception.OrderCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.Instant;

/**
 * Global exception handler for handling specific exceptions and returning structured error responses.
 */
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({
            OrderCreationException.class,
    })
    protected ResponseEntity<ErrorResponse> handleBadRequestException(Exception exception) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST
                , exception.getMessage()
                , Instant.now())
                , HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({
            NotFoundException.class,
    })
    protected ResponseEntity<ErrorResponse> handleNotFoundException(Exception exception) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND
                , exception.getMessage()
                , Instant.now())
                , HttpStatus.NOT_FOUND);
    }
}
