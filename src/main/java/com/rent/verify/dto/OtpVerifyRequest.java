package com.rent.verify.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Description: this class is responsible for TODO
 * vikas
 * @created on 26 Dec 2025
 * @version 1.0
 */
@Data
public class OtpVerifyRequest {
	@NotNull(message = "USER ID CAN NOT BLANK")
	private String userUid;
	private String mobile;
	private String otp;
}