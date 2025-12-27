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
@Getter
@Setter
@Entity
@Table(name = "OTP_TRANSACTION",
       indexes = {
           @Index(name = "IDX_OTP_USER", columnList = "USER_UID"),
           @Index(name = "IDX_OTP_MOBILE", columnList = "MOBILE_NUMBER")
       })
public class OtpTransactionEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(name = "USER_UID")
    private String userUid;

    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @Column(nullable = false)
    private String otp;

    @Column(name = "EXPIRES_AT", nullable = false)
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OtpStatus status;
}