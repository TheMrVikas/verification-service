package com.rent.verify.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.rent.verify.entity.EmailVerification;
import com.rent.verify.repository.EmailVerificationRepository;
import com.rent.verify.service.EmailVerificationService;

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
public class EmailVerificationServiceImpl implements EmailVerificationService {
	
	private final EmailVerificationRepository emailRepo;

	@Override
	public void verifyEmail(String token) {

		EmailVerification ev = emailRepo.findByToken(token).orElseThrow(() -> new RuntimeException("Invalid token"));

		if (ev.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("Email verification token expired");
		}

		ev.setVerified(true);
		emailRepo.save(ev);
	}

}
