package com.crowd.interactive.challenge.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.crowd.interactive.challenge.entity.BillingDetails;
import com.crowd.interactive.challenge.entity.Customer;

public class TestHelper {

    public static BillingDetails createBillingDetail() {
	return BillingDetails.builder().accountNumber("1234567899").tarrif(4.5).build();
    }

    public static Customer createCustomer() {
	return Customer.builder().firstName("frank").lastName("frank").email("frank.frank@gmail.com").build();
    }

    public static Customer customerResponse() {
	Customer customer = Customer.builder().firstName("frank").lastName("frank").email("frank.frank@gmail.com")
		.billingDetails(createBillingDetail()).build();
	customer.setCreatedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC));
	return customer;
    }

}
