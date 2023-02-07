package com.store.main;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.store.repositories.CustomerRepository;
import com.store.repositories.OccurrenceRepository;
import com.store.repositories.TelephoneRepository;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
class MainApplicationTests {

	@Autowired
    private CustomerRepository cRepository;

	@Autowired
    private TelephoneRepository tRepository;

	@Autowired
    private OccurrenceRepository oRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    @Autowired
    private Flyway flyway;

	@Test
	void contextLoads() throws Exception {
		Assertions.assertNotNull(cRepository);
		Assertions.assertNotNull(tRepository);
		Assertions.assertNotNull(oRepository);
		Assertions.assertNotNull(rabbitTemplate);
		Assertions.assertNotNull(queue);
		Assertions.assertNotNull(flyway);
	}

}
