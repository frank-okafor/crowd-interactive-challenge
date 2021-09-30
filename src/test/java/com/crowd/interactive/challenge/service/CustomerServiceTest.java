package com.crowd.interactive.challenge.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crowd.interactive.challenge.entity.BillingDetails;
import com.crowd.interactive.challenge.entity.Customer;
import com.crowd.interactive.challenge.exception.ServiceException;
import com.crowd.interactive.challenge.pojos.CustomerDTO;
import com.crowd.interactive.challenge.pojos.CustomerResponse;
import com.crowd.interactive.challenge.pojos.ServiceResponse;
import com.crowd.interactive.challenge.repository.BillingDetailsRepository;
import com.crowd.interactive.challenge.repository.CustomerRepository;
import com.crowd.interactive.challenge.util.TestHelper;
import com.crowd.interactive.challenge.utils.ApplicationUtils;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class CustomerServiceTest {
    private CustomerService service;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private BillingDetailsRepository billingDetailsRepository;

    @BeforeEach
    void setUp() throws Exception {
	service = new CustomerService(customerRepository, billingDetailsRepository);
    }

    @Test
    void testGetAllCustomers() {
	when(customerRepository.findAll()).thenReturn(List.of(TestHelper.customerResponse()));
	ServiceResponse response = service.getAllCustomers();
	@SuppressWarnings("unchecked")
	List<CustomerResponse> res = (List<CustomerResponse>) response.getData();
	assertNotNull(response.getData());
	assertThat(res.isEmpty()).isEqualTo(false);
    }

    @Test
    void testFindByEmail() {
	when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(TestHelper.customerResponse()));
	ServiceResponse response = service.findCustomerByEmail("test@gmail.com");
	CustomerResponse res = (CustomerResponse) response.getData();
	assertNotNull(response.getData());
	assertThat(res.getEmail()).isEqualTo("frank.frank@gmail.com");
    }

    @Test
    void testFindByAccountNumber() {
	when(billingDetailsRepository.findByAccountNumber(anyString()))
		.thenReturn(Optional.of(TestHelper.createBillingDetail()));
	when(customerRepository.findByBillingDetails(any(BillingDetails.class)))
		.thenReturn(Optional.of(TestHelper.customerResponse()));
	ServiceResponse response = service.findCustomerByAccountNumber("1234567543");
	CustomerResponse res = (CustomerResponse) response.getData();
	assertNotNull(response.getData());
	assertThat(res.getEmail()).isEqualTo("frank.frank@gmail.com");
    }

    @Test
    void testCreateNewCustomer() {
	when(customerRepository.checkIfEmailExists(anyString())).thenReturn(0);
	when(billingDetailsRepository.checkIfAccountExists(anyString())).thenReturn(0);
	when(customerRepository.save(any(Customer.class))).thenReturn(TestHelper.customerResponse());
	ServiceResponse response = service.createNewCustomer(ApplicationUtils.createDefaultCustomer());
	CustomerResponse res = (CustomerResponse) response.getData();
	assertNotNull(response.getData());
	assertThat(res.getEmail()).isEqualTo("frank.frank@gmail.com");
	verify(billingDetailsRepository, times(1)).save(any(BillingDetails.class));
    }

    @Test
    void testEmailExistError() {
	CustomerDTO dto = ApplicationUtils.createDefaultCustomer();
	when(customerRepository.checkIfEmailExists(anyString())).thenReturn(2);
	assertThatThrownBy(() -> service.createNewCustomer(dto)).isInstanceOf(ServiceException.class)
		.hasMessageContaining("email address " + dto.getEmail() + " already exist, please use another one");
	verify(customerRepository, never()).save(any());
	verify(billingDetailsRepository, never()).save(any());
    }

    @Test
    void testAccountNumberExistError() {
	CustomerDTO dto = ApplicationUtils.createDefaultCustomer();
	when(billingDetailsRepository.checkIfAccountExists(anyString())).thenReturn(2);
	assertThatThrownBy(() -> service.createNewCustomer(dto)).isInstanceOf(ServiceException.class)
		.hasMessageContaining(
			"account number " + dto.getAccountNumber() + " already exist, please use another one");
	verify(customerRepository, never()).save(any());
	verify(billingDetailsRepository, never()).save(any());
    }

}
