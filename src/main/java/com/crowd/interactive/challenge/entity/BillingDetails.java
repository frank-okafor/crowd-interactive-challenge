package com.crowd.interactive.challenge.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "billing_details")
@Data
@Builder
public class BillingDetails extends BaseEntity implements Serializable {

    @NotNull(message = "account number cannot be null")
    @NotBlank(message = "account number cannot be empty")
    @Column(unique = true, name = "account_number", nullable = false)
    private String accountNumber;
    private double tarrif = 0.0;
}
