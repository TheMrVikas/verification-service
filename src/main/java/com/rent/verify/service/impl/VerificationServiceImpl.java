package com.rent.verify.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rent.verify.entity.EmailVerification;
import com.rent.verify.entity.OtpTransactionEntity;
import com.rent.verify.enums.EmailStatus;
import com.rent.verify.enums.OtpStatus;
import com.rent.verify.exception.VerificationException;
import com.rent.verify.feign.NotificationClient;
import com.rent.verify.repository.EmailVerificationRepository;
import com.rent.verify.repository.OtpTransactionRepository;
import com.rent.verify.service.VerificationService;

import lombok.RequiredArgsConstructor;

/**
 * Description: this class is responsible for TODO
 * vikas
 * @created on 27 Dec 2025
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class VerificationServiceImpl implements VerificationService{

	private final OtpTransactionRepository otpRepo;
    private final EmailVerificationRepository emailRepo;
    private final NotificationClient notificationClient;
    //private final MailerService mailerService; // assume this sends actual emails
    
    private final Duration TOKEN_VALIDITY = Duration.ofMinutes(15);
    
    @Value("${notification.enabled:false}")
    private boolean notificationEnabled;
    
	@Override
	@Transactional
	public void generateOrResendOtp(String userUid, String mobile) {
		// 1. check if userid and mob number exist then resend otp
		OtpTransactionEntity tx = otpRepo.findTopByUserUidOrderByIdDesc(userUid).orElseGet(() -> {
			OtpTransactionEntity newTx = new OtpTransactionEntity();
			newTx.setUserUid(userUid);
			newTx.setMobileNumber(mobile);
			return newTx;
		});
		// UPDATE SAME ROW
		tx.setOtp(generateOtp());
		tx.setStatus(OtpStatus.SENT);
		tx.setExpiresAt(LocalDateTime.now().plusMinutes(5));
		otpRepo.save(tx);

		// smsService.sendOtp(mobile, otp);

		// 🔕 DB testing mode
		if (notificationEnabled) {
			notificationClient.sendOtpSms(mobile, tx.getOtp());
		}
	}

	@Override
	public void verifyOtp(String userUid, String otp) {
		OtpTransactionEntity tx = otpRepo.findTopByUserUidAndOtpOrderByIdDesc(userUid, otp)
				.orElseThrow(() -> new VerificationException("OTP not found"));
		if (tx.getExpiresAt().isBefore(LocalDateTime.now()) || !tx.getOtp().equals(otp)) {
			throw new VerificationException("OTP invalid or expired");
		}
		tx.setStatus(OtpStatus.VERIFIED);
		otpRepo.save(tx);
		// Update UserEntity.mobileVerified = true via UserService
	}

	@Override
	public void generateOrResendEmailVerification(String userUid, String email) {

		// Check if already exists and not expired
		emailRepo.findTopByUserUidAndEmailOrderByIdDesc(userUid, email).ifPresent(existing -> {
			if (existing.getStatus() == EmailStatus.PENDING && existing.getExpiresAt().isAfter(LocalDateTime.now())) {
				// still valid, resend mail
				// mailerService.sendEmail(email, "Verify your email",
				// buildEmailBody(existing.getToken()));
				return;
			}
		});

		// generate new token
		String token = UUID.randomUUID().toString();

		EmailVerification entity = new EmailVerification();
		entity.setUserUid(userUid);
		entity.setEmail(email);
		entity.setToken(token);
		entity.setStatus(EmailStatus.PENDING);
		entity.setExpiresAt(LocalDateTime.now().plus(TOKEN_VALIDITY));

		emailRepo.save(entity);

		// send mail
		// mailerService.sendEmail(email, "Verify your email", buildEmailBody(token));
	}

	@Override
	public void verifyEmail(String token) {

		EmailVerification entity = emailRepo.findByToken(token)
				.orElseThrow(() -> new VerificationException("Invalid email verification token"));

		// check expiry
		if (entity.getExpiresAt().isBefore(LocalDateTime.now())) {
			entity.setStatus(EmailStatus.EXPIRED);
			emailRepo.save(entity);
			throw new VerificationException("Token expired");
		}

		entity.setStatus(EmailStatus.VERIFIED);
		emailRepo.save(entity);
	}

	@Override
	public boolean isFullyVerified(String userUid) {
		// implement logic: check last OTP & Email verified status
		boolean mobileVerified = otpRepo.findTopByUserUidAndStatusOrderByIdDesc(userUid, OtpStatus.VERIFIED)
				.map(tx -> tx.getStatus() == OtpStatus.VERIFIED).orElse(false);

		boolean emailVerified = emailRepo.findTopByUserUidAndStatusOrderByIdDesc(userUid, EmailStatus.VERIFIED)
				.map(ev -> ev.getStatus() == EmailStatus.VERIFIED).orElse(false);

		return mobileVerified && emailVerified;
	}

	private String generateOtp() {
		return String.valueOf(100000 + new Random().nextInt(900000));
	}

	/*
	 * private String buildEmailBody(String token) { // customize as needed return
	 * "Please verify your email using this token: " + token; }
	 */

}

/*
if (notificationEnabled) {
notificationClient.sendEmailVerification(email, ev.getToken());
}*/

