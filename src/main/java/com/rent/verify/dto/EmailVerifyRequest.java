package com.rent.verify.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Description: this class is responsible for TODO
 * vikas
 * @created on 26 Dec 2025
 * @version 1.0
 */
@Data
public class EmailVerifyRequest {
	private String userUid;
	@NotBlank(message = "please enter valid token")
    private String token;
}