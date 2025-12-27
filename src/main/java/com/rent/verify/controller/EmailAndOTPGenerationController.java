package com.rent.verify.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rent.verify.dto.EmailGenerateRequest;
import com.rent.verify.dto.OtpVerifyRequest;
import com.rent.verify.service.EmailGenerationService;
import com.rent.verify.service.OtpGenerationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Description: this class is responsible for TODO
 * vikas
 * @created on 26 Dec 2025
 * @version 1.0
 */
@RestController
@RequestMapping("/generate")
@AllArgsConstructor
@Tag(
	    name = "OTP generation APIs",
	    description = "CRUD APIs for generate OTP"
	)
public class EmailAndOTPGenerationController {
	private final EmailGenerationService emailGenerationService;
	private final OtpGenerationService otpGenerationService;
	
	@PostMapping(value = "/email",produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "verify email", description = "place email for verification")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "email sent successfully"),
					@ApiResponse(responseCode = "400", description = "Invalid request data"),
					@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<String> generateEmail(@Valid @RequestBody EmailGenerateRequest request) {

		emailGenerationService.generateOrResendEmailVerification(request.getEmail());

		return ResponseEntity.ok("Verification email sent");
	}
	
	@PostMapping("/otp")
	@Operation(summary = "verify OTP", description = "place mobile number for verification")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OTP sent successfully"),
					@ApiResponse(responseCode = "400", description = "Invalid request data"),
					@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<String> generateOtp(@RequestBody OtpVerifyRequest request) {
		otpGenerationService.generateOrResendOtp(request.getMobile());
		return ResponseEntity.ok("OTP sent successfully");
	}
}
