package com.crowd.interactive.challenge.pojos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {

    private String accountNumber;
    private double tarrif;
    private String firstName;
    private String lastName;
    private String email;
    private String createdAt;
}
