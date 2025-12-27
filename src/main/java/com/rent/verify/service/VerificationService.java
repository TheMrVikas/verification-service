package com.rent.verify.service;

/**
 * Description: this class is responsible for TODO
 * vikas
 * @created on 27 Dec 2025
 * @version 1.0
 */
public interface VerificationService {
	/**
	 * 
	 * @param userUid
	 * @param mobile
	 * TODO
	 * void
	 */
	void generateOrResendOtp(String userUid, String mobile);

	/**
	 * 
	 * @param userUid
	 * @param otp
	 * TODO
	 * void
	 */
    void verifyOtp(String userUid, String otp);

    /**
     * 
     * @param userUid
     * @param email
     * TODO
     * void
     */
    void generateOrResendEmailVerification(String userUid, String email);
    /**
     * 
     * @param userUid
     * @return
     * TODO
     * boolean
     */
    boolean isFullyVerified(String userUid);

    void verifyEmail(String token);
}
