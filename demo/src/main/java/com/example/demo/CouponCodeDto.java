package com.example.demo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponCodeDto {

    private Long id;

    @NotBlank(message = "Coupon code is required")
    private String couponCode;

    @NotNull(message = "Discount value is required")
    private Long discountValue;

    @NotBlank(message = "Discount type is required")
    private String discountType;

    private String customerType;

    @NotNull(message = "Expiry date is required")
    private LocalDateTime expiryDate;

    private Long minimumDiscount;

    private Long maximumDiscount;

    private Long timesUsed;

}
