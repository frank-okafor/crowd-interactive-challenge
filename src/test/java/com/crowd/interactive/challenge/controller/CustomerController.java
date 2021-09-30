package com.crowd.interactive.challenge.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.crowd.interactive.challenge.entity.BillingDetails;
import com.crowd.interactive.challenge.entity.Customer;
import com.crowd.interactive.challenge.pojos.CustomerDTO;
import com.crowd.interactive.challenge.repository.BillingDetailsRepository;
import com.crowd.interactive.challenge.repository.CustomerRepository;
import com.crowd.interactive.challenge.util.TestHelper;
import com.crowd.interactive.challenge.utils.ApplicationUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class CustomerController {

    private @Autowired MockMvc mockMvc;
    private @Autowired ObjectMapper objectMapper;
    private @Autowired CustomerRepository customerRepository;
    private @Autowired BillingDetailsRepository billingDetailsRepository;

    @AfterEach
    void tearDown() throws Exception {
	customerRepository.deleteAll();
	billingDetailsRepository.deleteAll();
    }

    @Test
    void testGetAllCustomers() throws Exception {
	Customer customer = TestHelper.createCustomer();
	BillingDetails details = TestHelper.createBillingDetail();
	customer.setBillingDetails(billingDetailsRepository.save(details));
	customerRepository.save(customer);

	mockMvc.perform(get("/api/v1/customer").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.message").value("Customers retrieved successfully")).andReturn();
    }

    @Test
    void testGetCustomerByEmail() throws Exception {
	Customer customer = TestHelper.createCustomer();
	BillingDetails details = TestHelper.createBillingDetail();
	customer.setBillingDetails(billingDetailsRepository.save(details));
	customerRepository.save(customer);

	mockMvc.perform(get("/api/v1/customer/getbyemail").contentType(MediaType.APPLICATION_JSON).queryParam("email",
		customer.getEmail())).andExpect(status().isOk())
		.andExpect(jsonPath("$.message").value("Customer found successfully")).andReturn();
    }

    @Test
    void testGetCustomerByAccountNumber() throws Exception {
	Customer customer = TestHelper.createCustomer();
	BillingDetails details = TestHelper.createBillingDetail();
	customer.setBillingDetails(billingDetailsRepository.save(details));
	customerRepository.save(customer);

	mockMvc.perform(get("/api/v1/customer/" + details.getAccountNumber()).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(jsonPath("$.message").value("Customer found successfully"))
		.andReturn();
    }

    @Test
    void testCreateNewCustomer() throws Exception {
	CustomerDTO dto = ApplicationUtils.createDefaultCustomer();
	mockMvc.perform(post("/api/v1/customer").contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(dto))).andExpect(status().isOk());
	assertThat(customerRepository.findByEmail(dto.getEmail()).isPresent()).isEqualTo(true);
    }

}
