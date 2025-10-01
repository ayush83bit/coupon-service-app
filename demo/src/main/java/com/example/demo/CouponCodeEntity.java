package com.example.demo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "coupon_code")
@Getter
@Setter
public class CouponCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_code", unique = true, nullable = false)
    private String couponCode;

    @Column(name = "discount_value")
    private Long discountValue;

    @Column(name = "discount_type")
    private String discountType;

    @Column(name = "customer_type")
    private String customerType;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "minimum_discount")
    private Long minimumDiscount;

    @Column(name = "maximum_discount")
    private Long maximumDiscount;

    @Column(name = "times_used")
    private Long timesUsed;
}