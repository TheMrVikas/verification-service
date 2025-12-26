package com.rent.verify.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.rent.verify.entity.OtpTransactionEntity;
import com.rent.verify.enums.OtpStatus;
import com.rent.verify.repository.OtpTransactionRepository;
import com.rent.verify.service.OtpVerificationService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Description: this class is responsible for TODO
 * vikas
 * @created on 26 Dec 2025
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class OtpVerificationServiceImpl implements OtpVerificationService {
	
	private final OtpTransactionRepository otpRepo;
	
	@Override
	public void verifyOtp(String mobile, String otp) {

		OtpTransactionEntity tx = otpRepo.findOtpByMobileNumberOrderByIdDesc(mobile)
				.orElseThrow(() -> new RuntimeException("OTP not found"));

		if (tx.getExpiresAt().isBefore(LocalDateTime.now())) {
			tx.setStatus(OtpStatus.EXPIRED);
			otpRepo.save(tx);
			throw new RuntimeException("OTP expired");
		}

		if (!tx.getOtp().equals(otp)) {
			throw new RuntimeException("Invalid OTP");
		}

		tx.setStatus(OtpStatus.VERIFIED);
		otpRepo.save(tx);
	}
}
