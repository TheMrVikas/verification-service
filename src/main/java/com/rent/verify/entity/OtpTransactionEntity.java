package com.rent.verify.entity;

import java.time.LocalDateTime;

import com.rent.verify.enums.OtpStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Description: this class is responsible for TODO
 * vikas
 * @created on 26 Dec 2025
 * @version 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "OTP_TRANSACTION",
       indexes = {
         @Index(name = "IDX_OTP_MOBILE", columnList = "MOBILE_NUMBER"),
         @Index(name = "IDX_OTP_STATUS", columnList = "STATUS")
       })
public class OtpTransactionEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(unique = true)
    private String mobileNumber;
    private String otp;

    @Enumerated(EnumType.STRING)
    private OtpStatus status;

    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
}
