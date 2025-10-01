package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
class CustomerDiscountTypeDTO {

    private Long id;
    private Long customerId;
    private String customerName;
    private Long couponCodeId;
    private String couponCode;
    private LocalDateTime createdAt;
}