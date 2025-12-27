package com.rent.verify.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: this class is responsible for TODO
 * vikas
 * @created on 26 Dec 2025
 * @version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {

    private String message;
    private String errorCode;
    private int status;
    private LocalDateTime timestamp;

    public ApiErrorResponse(String message, String errorCode, int status) {
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}