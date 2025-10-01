package com.example.demo;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter
@Setter
class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_email_id", unique = true, nullable = false)
    private String customerEmailId;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Column(name = "customer_type")
    private String customerType;

}