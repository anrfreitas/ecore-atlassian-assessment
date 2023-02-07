package com.store.main.unit;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.store.enums.OrderBy;
import com.store.helpers.OrderByHelper;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class OrderByHelperTests {

    @Test
	void shouldReturnOrderByAsc() {
		assertSame(OrderBy.ASC, OrderByHelper.getOrderByOption("asc"));
	}

	@Test
	void shouldReturnOrderByDesc() {
		assertSame(OrderBy.DESC, OrderByHelper.getOrderByOption("desc"));
	}

	@Test
	void invalidKeyShouldReturnOrderByDesc() {
		assertSame(OrderBy.DESC, OrderByHelper.getOrderByOption("invalid_key"));
	}
}
