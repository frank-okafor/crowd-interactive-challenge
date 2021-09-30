package com.crowd.interactive.challenge.utils;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.crowd.interactive.challenge.entity.Customer;
import com.crowd.interactive.challenge.pojos.CustomerDTO;
import com.crowd.interactive.challenge.pojos.CustomerResponse;

@Component
public class ApplicationUtils {
    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
	    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private static final String ACCOUNT_NUMBER_PATTERN = "(\\+)?[0-9]{10}$";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
	    .withZone(ZoneId.systemDefault());

    public static boolean isEmailValid(final String email) {
	Pattern pattern = Pattern.compile(EMAIL_PATTERN);
	Matcher matcher = pattern.matcher(email);
	return matcher.matches();
    }

    public static boolean isAccountNumberValid(final String accountNumber) {
	Pattern pattern = Pattern.compile(ACCOUNT_NUMBER_PATTERN);
	Matcher matcher = pattern.matcher(accountNumber);
	return matcher.matches();
    }

    public static CustomerResponse convertCustomerToResponse(Customer customer) {
	return CustomerResponse.builder().accountNumber(customer.getBillingDetails().getAccountNumber())
		.createdAt(formatter.format(customer.getCreatedAt())).firstName(customer.getFirstName())
		.lastName(customer.getLastName()).email(customer.getEmail())
		.tarrif(customer.getBillingDetails().getTarrif()).build();
    }

    public static CustomerDTO createDefaultCustomer() {
	return CustomerDTO.builder().firstName("frank").lastName("okafor").email("frank.frank@gmail.com").tarrif(5.7)
		.accountNumber("1234567892").build();
    }
}
