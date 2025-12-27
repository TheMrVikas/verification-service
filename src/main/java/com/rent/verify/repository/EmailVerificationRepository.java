package com.rent.verify.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rent.verify.entity.EmailVerification;
import com.rent.verify.enums.EmailStatus;

/**
 * Description: this class is responsible for TODO
 * vikas
 * @created on 26 Dec 2025
 * @version 1.0
 */
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
	/**
	 * 
	 * @param token
	 * @return
	 * TODO
	 * EmailVerification
	 */
	Optional<EmailVerification> findByToken(String token);
	/**
	 * 
	 * @param email
	 * @return
	 * TODO
	 * List<EmailVerification>
	 */
	List<EmailVerification> findByEmail(String email);
	/**
	 * 
	 * @param userUid
	 * @param email
	 * @return
	 * TODO
	 * Optional<EmailVerification>
	 */
	Optional<EmailVerification> findTopByUserUidAndEmailOrderByIdDesc(String userUid, String email);
	/**
	 * 
	 * @param userUid
	 * @param status
	 * @return
	 * TODO
	 * Optional<EmailVerification>
	 */
	Optional<EmailVerification> findTopByUserUidAndStatusOrderByIdDesc(String userUid, EmailStatus status);

}
