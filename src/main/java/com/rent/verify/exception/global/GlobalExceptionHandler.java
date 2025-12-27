package com.rent.verify.exception.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rent.verify.dto.ApiErrorResponse;
import com.rent.verify.exception.VerificationException;

/**
 * Description: this class is responsible for TODO
 * vikas
 * @created on 26 Dec 2025
 * @version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(VerificationException.class)
	public ResponseEntity<ApiErrorResponse> handleVerificationException(VerificationException ex) {

		ApiErrorResponse response = new ApiErrorResponse(ex.getMessage(), "INVALID_OTP",
				HttpStatus.BAD_REQUEST.value());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	// Any unhandled exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex) {

		ApiErrorResponse response = new ApiErrorResponse("Something went wrong", "INTERNAL_SERVER_ERROR",
				HttpStatus.INTERNAL_SERVER_ERROR.value());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}
    