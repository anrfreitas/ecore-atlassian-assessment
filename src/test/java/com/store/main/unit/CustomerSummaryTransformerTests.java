package com.store.main.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.store.entities.Customer;
import com.store.transformer.CustomerSummaryTransformer;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class CustomerSummaryTransformerTests {

    @Test
	void shouldConvertCustomerCorrectly() {
		Customer customer = new Customer();
		customer.setId(99);
		customer.setName("John Doe");
		customer.setEmail("john@doe.com");
		CustomerSummaryTransformer transformed =
			new CustomerSummaryTransformer().toCustomerSummaryTransformer(customer);

		assertEquals(customer.getName(), transformed.getName());
		assertEquals(customer.getEmail(), transformed.getEmail());
	}

	@Test
	void shouldConvertListOfCustomersCorrectly() {
		List<Customer> listCustomer = new ArrayList<Customer>();

		int i = 0;
		for (i = 0; i < 10; i++) {
			Customer customer = new Customer();
			customer.setId(i);
			customer.setName("John Doe " + i);
			customer.setEmail(String.format("john%d@doe.com", i));
			listCustomer.add(customer);
		}

		List<CustomerSummaryTransformer> listTransformed =
			listCustomer.stream()
				.map(c -> new CustomerSummaryTransformer().toCustomerSummaryTransformer(c))
				.collect(Collectors.toList());

		for (i = 0; i < listTransformed.size(); i++) {
			assertEquals(listCustomer.get(i).getName(), listTransformed.get(i).getName());
			assertEquals(listCustomer.get(i).getEmail(), listTransformed.get(i).getEmail());
		}
	}
}
