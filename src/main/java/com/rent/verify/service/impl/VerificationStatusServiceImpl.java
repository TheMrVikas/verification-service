package com.rent.verify.service.impl;

import org.springframework.stereotype.Service;

import com.rent.verify.enums.OtpStatus;
import com.rent.verify.repository.EmailVerificationRepository;
import com.rent.verify.repository.OtpTransactionRepository;
import com.rent.verify.service.VerificationStatusService;

import lombok.RequiredArgsConstructor;

/**
 * Description: this class is responsible for TODO
 * vikas
 * @created on 26 Dec 2025
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class VerificationStatusServiceImpl implements VerificationStatusService{
	
	private final OtpTransactionRepository otpRepo;
    private final EmailVerificationRepository emailRepo;
	
    @Override
    public boolean isFullyVerified(String mobile, String email) {

        boolean otpVerified =
            otpRepo.existsByMobileNumberAndStatus(mobile, OtpStatus.VERIFIED);

        boolean emailVerified =
            emailRepo.existsByEmailAndVerifiedTrue(email);

        return otpVerified && emailVerified;
    }

}
