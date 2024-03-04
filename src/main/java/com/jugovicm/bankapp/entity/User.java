package com.jugovicm.bankapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fistName;
    private String lastName;
    private String otherName;
    private String gender;
    private String address;
    private String accountNumber;
    private BigDecimal accountBalance;
    private String email;
    private String phoneNumber;
    private String alternativePhoneNumber;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
}
