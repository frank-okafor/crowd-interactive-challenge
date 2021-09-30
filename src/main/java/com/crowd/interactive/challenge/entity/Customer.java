package com.crowd.interactive.challenge.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "customer")
@Data
@Builder
public class Customer extends BaseEntity implements Serializable {

    @ToString.Include
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "billing_details_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BillingDetails billingDetails;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Email
    @Column(unique = true, name = "email_address", nullable = false)
    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email cannot be empty")
    private String email;
}
