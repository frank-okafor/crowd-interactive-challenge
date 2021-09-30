package com.crowd.interactive.challenge.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crowd.interactive.challenge.pojos.CustomerDTO;
import com.crowd.interactive.challenge.pojos.ServiceResponse;
import com.crowd.interactive.challenge.service.CustomerService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @PostMapping
    @ApiOperation(value = "create a new customer", notes = "email address is compulsory and must be valid, "
	    + "account number is compulsory and must be numbers only and not more or less than ten characters")
    public ResponseEntity<ServiceResponse> createNewCustomer(@Valid @RequestBody CustomerDTO dto) {
	ServiceResponse response = service.createNewCustomer(dto);
	return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/{accountnumber}")
    @ApiOperation(value = "get customer by account number")
    public ResponseEntity<ServiceResponse> findCustomerByAccountNumber(
	    @PathVariable("accountnumber") String accountNumber) {
	ServiceResponse response = service.findCustomerByAccountNumber(accountNumber);
	return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/getbyemail")
    @ApiOperation(value = "get customer by email address")
    public ResponseEntity<ServiceResponse> findCustomerByEmail(
	    @RequestParam(value = "email", required = true) String email) {
	ServiceResponse response = service.findCustomerByEmail(email);
	return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping
    @ApiOperation(value = "get all customers in the system")
    public ResponseEntity<ServiceResponse> getAllCustomers() {
	ServiceResponse response = service.getAllCustomers();
	return new ResponseEntity<>(response, response.getStatus());
    }
}
