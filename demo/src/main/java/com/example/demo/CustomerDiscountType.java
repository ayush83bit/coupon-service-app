package com.example.demo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
class CustomerDiscountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customerEntity;


    @ManyToOne
    @JoinColumn
    private CouponCodeEntity couponCodeEntity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


}
