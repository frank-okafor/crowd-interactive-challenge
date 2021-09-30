package com.crowd.interactive.challenge.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.crowd.interactive.challenge.entity.BillingDetails;
import com.crowd.interactive.challenge.entity.Customer;
import com.crowd.interactive.challenge.exception.ServiceException;
import com.crowd.interactive.challenge.pojos.CustomerDTO;
import com.crowd.interactive.challenge.pojos.CustomerResponse;
import com.crowd.interactive.challenge.pojos.ServiceResponse;
import com.crowd.interactive.challenge.repository.BillingDetailsRepository;
import com.crowd.interactive.challenge.repository.CustomerRepository;
import com.crowd.interactive.challenge.utils.ApplicationUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final BillingDetailsRepository billingDetailsRepository;

    public ServiceResponse findCustomerByEmail(String email) {
	return customerRepository.findByEmail(email).map(customer -> {
	    return ServiceResponse.builder().message("Customer found successfully")
		    .timestamp(LocalDateTime.now().toString()).status(HttpStatus.OK)
		    .data(ApplicationUtils.convertCustomerToResponse(customer)).build();
	}).orElseThrow(
		() -> new ServiceException(HttpStatus.NOT_FOUND, "Customer with email " + email + " does not exist"));
    }

    public ServiceResponse findCustomerByAccountNumber(String accountNumber) {
	return billingDetailsRepository.findByAccountNumber(accountNumber).map(details -> {
	    Customer customer = customerRepository.findByBillingDetails(details).get();
	    return ServiceResponse.builder().message("Customer found successfully")
		    .timestamp(LocalDateTime.now().toString()).status(HttpStatus.OK)
		    .data(ApplicationUtils.convertCustomerToResponse(customer)).build();
	}).orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND,
		"Customer with account number " + accountNumber + " does not exist"));
    }

    public ServiceResponse getAllCustomers() {
	List<CustomerResponse> response = customerRepository.findAll().parallelStream()
		.map(customer -> ApplicationUtils.convertCustomerToResponse(customer)).collect(Collectors.toList());
	String message = "Customers retrieved successfully";
	if (response.isEmpty()) {
	    message = "no records found";
	}
	return ServiceResponse.builder().message(message).timestamp(LocalDateTime.now().toString())
		.status(HttpStatus.OK).data(response).build();
    }

    public ServiceResponse createNewCustomer(CustomerDTO dto) {
	if (!ApplicationUtils.isEmailValid(dto.getEmail())) {
	    throw new ServiceException(HttpStatus.BAD_REQUEST, "email address is invalid");
	}
	if (!ApplicationUtils.isAccountNumberValid(dto.getAccountNumber())) {
	    throw new ServiceException(HttpStatus.BAD_REQUEST, "account number must be ten character numbers only");
	}
	if (customerRepository.checkIfEmailExists(dto.getEmail()) > 0) {
	    throw new ServiceException(HttpStatus.BAD_REQUEST,
		    "email address " + dto.getEmail() + " already exist, please use another one");
	}
	if (billingDetailsRepository.checkIfAccountExists(dto.getAccountNumber().concat("-01")) > 0) {
	    throw new ServiceException(HttpStatus.BAD_REQUEST,
		    "account number " + dto.getAccountNumber() + " already exist, please use another one");
	}
	BillingDetails details = BillingDetails.builder().accountNumber(dto.getAccountNumber().concat("-01"))
		.tarrif(dto.getTarrif()).build();
	Customer customer = Customer.builder().firstName(dto.getFirstName()).lastName(dto.getLastName())
		.email(dto.getEmail()).billingDetails(billingDetailsRepository.save(details)).build();
	return ServiceResponse.builder().message("Customer created successfully")
		.timestamp(LocalDateTime.now().toString()).status(HttpStatus.CREATED)
		.data(ApplicationUtils.convertCustomerToResponse(customerRepository.save(customer))).build();
    }

    @PostConstruct
    private void init() {
	createNewCustomer(ApplicationUtils.createDefaultCustomer());
    }

}
