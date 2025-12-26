package com.rent.verify.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rent.verify.entity.OtpTransactionEntity;
import com.rent.verify.enums.OtpStatus;

/**
 * Description: this class is responsible for TODO
 * vikas
 * @created on 26 Dec 2025
 * @version 1.0
 */
@Repository
public interface OtpTransactionRepository extends JpaRepository<OtpTransactionEntity, Long>{
	/**
	 * 
	 * @param mobile
	 * @return
	 * TODO
	 * OtpTransactionEntity
	 */
	Optional<OtpTransactionEntity> findOtpByMobileNumberOrderByIdDesc(String mobile);
	/**
	 * 
	 * @param mobile
	 * @param status
	 * @return
	 * TODO
	 * boolean
	 */
	boolean existsByMobileNumberAndStatus(String mobile, OtpStatus status);
}
