package com.rent.verify.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Description: this class is responsible for TODO
 * vikas
 * @created on 26 Dec 2025
 * @version 1.0
 */
@Data
public class EmailGenerateRequest {
	@NotBlank(message = "email is mandatory")
	@Email(message = "email should be correct")
	private String email;
}
