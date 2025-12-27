package com.rent.verify.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rent.verify.dto.EmailGenerateRequest;
import com.rent.verify.dto.EmailVerifyRequest;
import com.rent.verify.dto.OtpVerifyRequest;
import com.rent.verify.service.VerificationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;
import lombok.AllArgsConstructor;

/**
 * Description: this class is responsible for TODO vikas
 * 
 * @created on 26 Dec 2025
 * @version 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/verification")
@Tag(name = "OTP and Email verification APIs", description = "CRUD APIs for verification")
public class VerificationController {

	private final VerificationService verificationService;

	@Operation(summary = "Generate / Resend Email Verification", description = "Send verification email using userUid and email")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Verification email sent"),
			@ApiResponse(responseCode = "400", description = "Invalid request"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PostMapping(value = "/email", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> generateEmail(@Valid @RequestBody EmailGenerateRequest request) {
		verificationService.generateOrResendEmailVerification(request.getUserUid(), request.getEmail());
		return ResponseEntity.ok("Verification email sent");
	}

	@Operation(summary = "Verify Email", description = "Verify email using token and userUid")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Email verified successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid or expired token"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PostMapping(value = "/email/verify", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> verifyEmail(@Valid @RequestBody EmailVerifyRequest request) {
		verificationService.verifyEmail(request.getToken());
		return ResponseEntity.ok("Email verified successfully");
	}

	@Operation(summary = "Generate / Resend OTP", description = "Send OTP to mobile number using userUid")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OTP sent successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid request"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PostMapping(value = "/otp", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> generateOtp(@RequestBody OtpVerifyRequest request) {
		verificationService.generateOrResendOtp(request.getUserUid(), request.getMobile());
		return ResponseEntity.ok("OTP sent successfully");
	}

	@Operation(summary = "Verify OTP", description = "Verify OTP using userUid and otp")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OTP verified successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid or expired OTP"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PostMapping(value = "/otp/verify", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> verifyOtp(@RequestBody OtpVerifyRequest request) {

		verificationService.verifyOtp(request.getUserUid(), request.getOtp());

		return ResponseEntity.ok("OTP verified successfully");
	}

	@Operation(summary = "Check verification status", description = "Check if user is fully verified (email + mobile)")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Verification status returned"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@GetMapping(value = "/status/{userUid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Boolean>> verificationStatus(@PathVariable("userUid")String userUid) {
		boolean verified = verificationService.isFullyVerified(userUid);
		Map<String, Boolean> response = new HashMap<>();
		response.put("fullyVerified", verified);
		return ResponseEntity.ok(response);
	}
}
