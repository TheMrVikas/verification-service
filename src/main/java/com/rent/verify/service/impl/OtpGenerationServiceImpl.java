package com.rent.verify.service.impl;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rent.verify.entity.OtpTransactionEntity;
import com.rent.verify.enums.OtpStatus;
import com.rent.verify.feign.NotificationClient;
import com.rent.verify.repository.OtpTransactionRepository;
import com.rent.verify.service.OtpGenerationService;

import lombok.RequiredArgsConstructor;

/**
 * Description: this class is responsible for TODO
 * vikas
 * @created on 26 Dec 2025
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class OtpGenerationServiceImpl implements OtpGenerationService {
	
	private final OtpTransactionRepository otpRepo;
	private final NotificationClient notificationClient;
	
	@Value("${notification.enabled:false}")
    private boolean notificationEnabled;
    
	@Override
	public void generateOrResendOtp(String mobile) {

		OtpTransactionEntity tx = new OtpTransactionEntity();
		tx.setMobileNumber(mobile);
		tx.setOtp(generateOtp());
		tx.setStatus(OtpStatus.SENT);
		tx.setExpiresAt(LocalDateTime.now().plusMinutes(5));
		tx.setCreatedAt(LocalDateTime.now());

		otpRepo.save(tx);

		// 🔕 DB testing mode
		if (notificationEnabled) {
			notificationClient.sendOtpSms(mobile, tx.getOtp());
		}
	}

	private String generateOtp() {
		return String.valueOf(100000 + new Random().nextInt(900000));
	}
    

}
