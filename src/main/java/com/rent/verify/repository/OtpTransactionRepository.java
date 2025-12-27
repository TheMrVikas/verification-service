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
	Optional<OtpTransactionEntity> findTopByUserUidAndOtpOrderByIdDesc(String userUid, String otp);
	/**
	 * 
	 * @param mobile
	 * @param status
	 * @return
	 * TODO
	 * boolean
	 */
	boolean existsByMobileNumberAndUserUid(String mobile, String userid);
	/**
	 * 
	 * @param userUid
	 * @param status
	 * @return
	 * TODO
	 * Optional<OtpTransactionEntity>
	 */
	Optional<OtpTransactionEntity> findTopByUserUidAndStatusOrderByIdDesc(String userUid, OtpStatus status);
	/**
	 * 
	 * @param userid
	 * @param mob
	 * @return
	 * TODO
	 * Optional<OtpTransactionEntity>
	 */
	Optional<OtpTransactionEntity> findTopByUserUidAndMobileNumberOrderByIdDesc(String userid, String mob);
	
	Optional<OtpTransactionEntity> findTopByUserUidOrderByIdDesc(String userid);

}
