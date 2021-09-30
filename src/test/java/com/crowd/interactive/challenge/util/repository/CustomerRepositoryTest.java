package com.crowd.interactive.challenge.util.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.crowd.interactive.challenge.entity.BillingDetails;
import com.crowd.interactive.challenge.entity.Customer;
import com.crowd.interactive.challenge.repository.BillingDetailsRepository;
import com.crowd.interactive.challenge.repository.CustomerRepository;
import com.crowd.interactive.challenge.util.TestHelper;

@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
@TestInstance(Lifecycle.PER_CLASS)
class CustomerRepositoryTest {
    private @Autowired CustomerRepository customerRepository;
    private @Autowired BillingDetailsRepository billingDetailsRepository;

    @BeforeEach
    void setUp() throws Exception {
	customerRepository.deleteAll();
	billingDetailsRepository.deleteAll();
    }

    @Test
    void testFindUserByEmail() {
	Customer customer = TestHelper.createCustomer();
	BillingDetails details = TestHelper.createBillingDetail();
	customer.setBillingDetails(billingDetailsRepository.save(details));
	customerRepository.save(customer);
	assertThat(customerRepository.findByEmail(customer.getEmail()).isPresent()).isTrue();
    }

    @Test
    void testFindUserByBillingDetails() {
	Customer customer = TestHelper.createCustomer();
	BillingDetails details = TestHelper.createBillingDetail();
	customer.setBillingDetails(billingDetailsRepository.save(details));
	customerRepository.save(customer);
	assertThat(customerRepository.findByBillingDetails(details).isPresent()).isTrue();
    }

    @Test
    void testFindUserByAccountNumber() {
	BillingDetails details = TestHelper.createBillingDetail();
	billingDetailsRepository.save(details);
	assertThat(billingDetailsRepository.findByAccountNumber(details.getAccountNumber()).isPresent()).isTrue();
    }

    @Test
    void testCheckIfEmailExist() {
	Customer customer = TestHelper.createCustomer();
	BillingDetails details = TestHelper.createBillingDetail();
	customer.setBillingDetails(billingDetailsRepository.save(details));
	customerRepository.save(customer);
	assertThat(customerRepository.checkIfEmailExists(customer.getEmail()) > 0).isTrue();
    }

    @Test
    void testCheckIfAccountNumberExist() {
	BillingDetails details = TestHelper.createBillingDetail();
	billingDetailsRepository.save(details);
	assertThat(billingDetailsRepository.checkIfAccountExists(details.getAccountNumber()) > 0).isTrue();
    }

}
