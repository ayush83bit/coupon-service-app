package com.example.demo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponCodeService {

    private final CouponCodeRepository couponCodeRepository;
    private final CustomerRepository customerRepository;
    private final CustomerDiscountTypeRepository customerDiscountTypeRepository;

    @Transactional
    public CouponCodeDto createCoupon(CouponCodeDto couponDto) {
        CouponCodeEntity entity = toEntity(couponDto);
        CouponCodeEntity saved = couponCodeRepository.save(entity);
        return toDto(saved);
    }

    public CouponCodeDto getCouponById(Long id) {
        CouponCodeEntity entity = couponCodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + id));
        return toDto(entity);
    }

    @Transactional
    public CouponCodeDto updateCoupon(Long id, CouponCodeDto couponDto) {
        CouponCodeEntity existing = couponCodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + id));

        if (couponDto.getCouponCode() != null) existing.setCouponCode(couponDto.getCouponCode());
        if (couponDto.getDiscountValue() != null) existing.setDiscountValue(couponDto.getDiscountValue());
        if (couponDto.getDiscountType() != null) existing.setDiscountType(couponDto.getDiscountType());
        if (couponDto.getCustomerType() != null) existing.setCustomerType(couponDto.getCustomerType());
        if (couponDto.getExpiryDate() != null) existing.setExpiryDate(couponDto.getExpiryDate());
        if (couponDto.getMinimumDiscount() != null) existing.setMinimumDiscount(couponDto.getMinimumDiscount());
        if (couponDto.getMaximumDiscount() != null) existing.setMaximumDiscount(couponDto.getMaximumDiscount());
        if (couponDto.getTimesUsed() != null) existing.setTimesUsed(couponDto.getTimesUsed());

        CouponCodeEntity saved = couponCodeRepository.save(existing);
        return toDto(saved);
    }

    @Transactional
    public void deleteCoupon(Long id) {
        if (!couponCodeRepository.existsById(id)) {
            throw new RuntimeException("Coupon not found with id: " + id);
        }
        List<CustomerDiscountType> mappings = customerDiscountTypeRepository.findByCouponCodeEntityId(id);
        if (mappings != null && !mappings.isEmpty()) {
            customerDiscountTypeRepository.deleteAll(mappings);
        }
        couponCodeRepository.deleteById(id);
    }

    @Transactional
    public void assignCouponToCustomers(Long couponId, List<Long> customerIds) {
        CouponCodeEntity coupon = couponCodeRepository.findById(couponId)
                .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + couponId));

        for (Long customerId : customerIds) {
            CustomerEntity customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

            boolean alreadyAssigned = customerDiscountTypeRepository
                    .existsByCustomerEntityIdAndCouponCodeEntityId(customerId, couponId);
            if (alreadyAssigned) continue;

            CustomerDiscountType mapping = new CustomerDiscountType();
            mapping.setCustomerEntity(customer);
            mapping.setCouponCodeEntity(coupon);
            mapping.setCreatedAt(LocalDateTime.now());
            customerDiscountTypeRepository.save(mapping);
        }
    }

    public List<CustomerDto> getCouponCustomers(Long couponId) {
        if (!couponCodeRepository.existsById(couponId)) {
            throw new RuntimeException("Coupon not found with id: " + couponId);
        }

        List<CustomerDiscountType> mappings = customerDiscountTypeRepository.findByCouponCodeEntityId(couponId);

        return mappings.stream()
                .map(m -> {
                    CustomerEntity c = m.getCustomerEntity();
                    CustomerDto dto = new CustomerDto();
                    dto.setCustomerId(c.getCustomerId());
                    try {
                        dto.setCustomerName(c.getCustomerName());
                    } catch (Throwable ignored) { }
                    try {
                        dto.setPhoneNumber(c.getPhoneNumber());
                    } catch (Throwable ignored) { }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private CouponCodeEntity toEntity(CouponCodeDto dto) {
        CouponCodeEntity e = new CouponCodeEntity();
        e.setId(dto.getId());
        e.setCouponCode(dto.getCouponCode());
        e.setDiscountValue(dto.getDiscountValue());
        e.setDiscountType(dto.getDiscountType());
        e.setCustomerType(dto.getCustomerType());
        e.setExpiryDate(dto.getExpiryDate());
        e.setMinimumDiscount(dto.getMinimumDiscount());
        e.setMaximumDiscount(dto.getMaximumDiscount());
        e.setTimesUsed(dto.getTimesUsed());
        return e;
    }

    private CouponCodeDto toDto(CouponCodeEntity e) {
        CouponCodeDto dto = new CouponCodeDto();
        dto.setId(e.getId());
        dto.setCouponCode(e.getCouponCode());
        dto.setDiscountValue(e.getDiscountValue());
        dto.setDiscountType(e.getDiscountType());
        dto.setCustomerType(e.getCustomerType());
        dto.setExpiryDate(e.getExpiryDate());
        dto.setMinimumDiscount(e.getMinimumDiscount());
        dto.setMaximumDiscount(e.getMaximumDiscount());
        dto.setTimesUsed(e.getTimesUsed());
        return dto;
    }
}
