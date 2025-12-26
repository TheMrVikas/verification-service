package com.rent.verify.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rent.verify.entity.EmailVerification;
import com.rent.verify.feign.NotificationClient;
import com.rent.verify.repository.EmailVerificationRepository;
import com.rent.verify.service.EmailGenerationService;

import lombok.RequiredArgsConstructor;

/**
 * Description: this class is responsible for TODO
 * vikas
 * @created on 26 Dec 2025
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class EmailGenerationServiceImpl implements EmailGenerationService{
	
	private final EmailVerificationRepository emailRepo;
    private final NotificationClient notificationClient;
    
    @Value("${notification.enabled:false}")
    private boolean notificationEnabled;
	
	@Override
	public void generateOrResendEmailVerification(String email) {

		EmailVerification ev = new EmailVerification();
		ev.setEmail(email);
		ev.setToken(UUID.randomUUID().toString());
		ev.setVerified(false);
		ev.setExpiresAt(LocalDateTime.now().plusHours(24));

		emailRepo.save(ev);

		if (notificationEnabled) {
			notificationClient.sendEmailVerification(email, ev.getToken());
		}
	}

}
