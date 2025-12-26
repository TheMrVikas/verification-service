package com.rent.verify.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Description: this class is responsible for TODO
 * vikas
 * @created on 26 Dec 2025
 * @version 1.0
 */
@FeignClient(name = "09-NOTIFICATION-SERVICE",path = "/internal/notification")
public interface NotificationClient {
	/**
	 * 
	 * @param mobile
	 * @param otp
	 * TODO
	 * void
	 */
	@PostMapping("/sms/otp")
	void sendOtpSms(@RequestParam(value = "mobile") String mobile, @RequestParam(value = "otp") String otp);
	
	/**
	 * 
	 * @param email
	 * @param token
	 * TODO
	 * void
	 */
	@PostMapping("/email/verify")
	void sendEmailVerification(@RequestParam(value = "email") String email, @RequestParam(value = "token") String token);
}