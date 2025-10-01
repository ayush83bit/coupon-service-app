package com.example.demo;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class CustomerDto {

    private Long customerId;

    private String CustomerName;

    private Email CustomerEmailId;

    private Long PhoneNumber;
}

