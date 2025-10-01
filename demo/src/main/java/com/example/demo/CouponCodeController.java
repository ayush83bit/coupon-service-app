package com.example.demo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponCodeController {

    private final CouponCodeService couponService;

    // Create a new coupon
    @PostMapping
    public ResponseEntity<CouponCodeDto> createCoupon(@Valid @RequestBody CouponCodeDto couponDto) {
        CouponCodeDto created = couponService.createCoupon(couponDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponCodeDto> getCouponById(@PathVariable Long id) {
        CouponCodeDto coupon = couponService.getCouponById(id);
        return ResponseEntity.ok(coupon);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CouponCodeDto> updateCoupon(
            @PathVariable Long id,
            @Valid @RequestBody CouponCodeDto couponDto) {
        CouponCodeDto updated = couponService.updateCoupon(id, couponDto);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{couponId}/assign")
    public ResponseEntity<String> assignCouponToCustomers(
            @PathVariable Long couponId,
            @RequestBody List<Long> customerIds) {
        couponService.assignCouponToCustomers(couponId, customerIds);
        return ResponseEntity.ok("Coupon assigned successfully");
    }

    @GetMapping("/{couponId}/customers")
    public ResponseEntity<List<CustomerDto>> getCouponCustomers(@PathVariable Long couponId) {
        List<CustomerDto> customers = couponService.getCouponCustomers(couponId);
        return ResponseEntity.ok(customers);
    }
}
