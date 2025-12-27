package com.rent.verify.entity;

import java.time.LocalDateTime;

import com.rent.verify.enums.EmailStatus;

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
@Table(name = "EMAIL_VERIFICATION",
       indexes = {
           @Index(name = "IDX_EMAIL_USER", columnList = "USER_UID"),
           @Index(name = "IDX_EMAIL_EMAIL", columnList = "EMAIL")
       })
public class EmailVerification extends AuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_UID")
    private String userUid;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "EXPIRES_AT")
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmailStatus status;
}