package com.example.orderservice.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;

/**
 * Represents the structure of an error response returned to the client.
 */
@Data
@AllArgsConstructor
public class ErrorResponse {

    private HttpStatus status;
    private String message;
    private Instant timestamp;
}
