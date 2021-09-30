package com.crowd.interactive.challenge.pojos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
@Builder
public class CustomerDTO {

    @NotNull(message = "account number cannot be null")
    @NotBlank(message = "account number cannot be empty")
    private String accountNumber;
    private double tarrif;
    private String firstName;
    private String lastName;
    @NotNull(message = "account number cannot be null")
    @NotBlank(message = "account number cannot be empty")
    private String email;

}
