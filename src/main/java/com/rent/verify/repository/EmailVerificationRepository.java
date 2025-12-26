package com.rent.verify.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rent.verify.entity.EmailVerification;

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
	 * boolean
	 */
	boolean existsByEmailAndVerifiedTrue(String email);
	/**
	 * 
	 * @param email
	 * @return
	 * TODO
	 * List<EmailVerification>
	 */
	List<EmailVerification> findByEmail(String email);
	
}
