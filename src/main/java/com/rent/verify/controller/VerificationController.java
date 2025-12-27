package com.rent.verify.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rent.verify.dto.EmailVerifyRequest;
import com.rent.verify.dto.OtpVerifyRequest;
import com.rent.verify.service.EmailVerificationService;
import com.rent.verify.service.OtpVerificationService;
import com.rent.verify.service.VerificationStatusService;

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
@AllArgsConstructor
@RequestMapping("/verification")
@Tag(
	    name = "OTP and Email verification APIs",
	    description = "CRUD APIs for verification"
	)
public class VerificationController {
	
	 private final OtpVerificationService otpService;
	 private final EmailVerificationService emailService;
	 private final VerificationStatusService statusService;
	 
	 
		@Operation(summary = "verify OTP", description = "place mobile number and opt for verification")
		@ApiResponses({ @ApiResponse(responseCode = "200", description = "OTP verified successfully"),
						@ApiResponse(responseCode = "400", description = "Invalid request data"),
						@ApiResponse(responseCode = "500", description = "Internal server error") })
		@PostMapping(value = "/otp/verify",produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<String> verifyOtp(@RequestBody OtpVerifyRequest request) {
			otpService.verifyOtp(request.getMobile(), request.getOtp());
			return ResponseEntity.ok("OTP verified");
		}

		@Operation(summary = "verify email", description = "place email and opt for verification")
		@ApiResponses({ @ApiResponse(responseCode = "201", description = "email verified successfully"),
						@ApiResponse(responseCode = "400", description = "Invalid request data"),
						@ApiResponse(responseCode = "500", description = "Internal server error") })
		@PostMapping(value = "/email/verify",produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<String> verifyEmail(@Valid @RequestBody EmailVerifyRequest request) {
			emailService.verifyEmail(request.getToken());
			return ResponseEntity.ok("Email verified");
		}
		
		@Operation(summary = "verification status check", description = "place email and mobile to check verification status")
		@ApiResponses({ @ApiResponse(responseCode = "200", description = "email and mobile verified successfully"),
						@ApiResponse(responseCode = "400", description = "Invalid request data"),
						@ApiResponse(responseCode = "500", description = "Internal server error") })
		@GetMapping(value = "/status",produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Map<String, Boolean>> isFullyVerified(@RequestParam(name = "mobile") String mobile,
																	@RequestParam(name = "email") String email) {
			boolean verified = statusService.isFullyVerified(mobile, email);
			Map<String, Boolean> response = new HashMap<>();
			response.put("fullyVerified", verified);
			return ResponseEntity.ok(response);
		}

}
