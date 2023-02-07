package com.store.main.integration;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import net.minidev.json.JSONObject;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerControllerTests {

    @Value("${local.server.port}")
	private String port;

    @Value("${server.servlet.contextPath}")
	private String contextPath;

    private final String SERVICE_PATH = "/customer";

    private final Integer NEXT_SEQUENCE = 100;

    @Autowired
	private TestRestTemplate restTemplate;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    void beforeEach() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
	void postMethodShouldCreateNewCustomer() {
        JSONObject payload = new JSONObject();
        payload.put("name", "andre");
        payload.put("email", "andre@email.com");

        ResponseEntity<JSONObject> response =
            this.restTemplate.postForEntity(
                this.getURI(""),
                payload,
                JSONObject.class
            );

        Assertions.assertTrue(response.getStatusCode() == HttpStatus.CREATED);
        Assertions.assertEquals(NEXT_SEQUENCE, response.getBody().get("id"));
        Assertions.assertEquals(payload.get("name"), response.getBody().get("name"));
        Assertions.assertEquals(payload.get("email"), response.getBody().get("email"));
    }

    @Test
	void postMethodShouldReturnValidationError() {
        JSONObject payload = new JSONObject();
        payload.put("name", "andre");
        payload.put("email", "invalid_email");

        ResponseEntity<JSONObject> response =
            this.restTemplate.postForEntity(
                this.getURI(""),
                payload,
                JSONObject.class
            );

        Assertions.assertTrue(response.getStatusCode() == HttpStatus.BAD_REQUEST);
    }

    @Test
	void postMethodShouldReturnConflictError() {
        JSONObject payload = new JSONObject();
        payload.put("name", "andre");
        payload.put("email", "user1@gmail.com");

        ResponseEntity<JSONObject> response =
            this.restTemplate.postForEntity(
                this.getURI(""),
                payload,
                JSONObject.class
            );

        Assertions.assertTrue(response.getStatusCode() == HttpStatus.CONFLICT);
    }

    @Test
	void putMethodShouldUpdateCustomer() {
        JSONObject payload = new JSONObject();
        payload.put("name", "my new name");

        HttpEntity<JSONObject> httpEntity = new HttpEntity<JSONObject>(payload);

        ResponseEntity<JSONObject> response =
            this.restTemplate.exchange(
                this.getURI("/1"),
                HttpMethod.PUT,
                httpEntity,
                JSONObject.class
            );

        Assertions.assertTrue(response.getStatusCode() == HttpStatus.OK);
        Assertions.assertEquals(1, response.getBody().get("id"));
        Assertions.assertEquals("user1@gmail.com", response.getBody().get("email"));
        Assertions.assertEquals(payload.get("name"), response.getBody().get("name"));
    }

    @Test
	void getMethodShouldBringCustomerData() {
        ResponseEntity<JSONObject> response =
            this.restTemplate.getForEntity(
                this.getURI("/2"),
                JSONObject.class
            );

        Assertions.assertTrue(response.getStatusCode() == HttpStatus.OK);
        Assertions.assertEquals(2, response.getBody().get("id"));
        Assertions.assertEquals("user2", response.getBody().get("name"));
        Assertions.assertEquals("user2@gmail.com", response.getBody().get("email"));
        Assertions.assertNotNull(response.getBody().get("telephones"));
    }

    @Test
	void getMethodShouldReturnNotFoundError() {
        ResponseEntity<JSONObject> response =
            this.restTemplate.getForEntity(
                this.getURI("/99"),
                JSONObject.class
            );

        Assertions.assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND);
    }

    @Test
	void deleteMethodShouldBringCustomerData() {
        ResponseEntity<JSONObject> response =
            this.restTemplate.exchange(
                this.getURI("/1"),
                HttpMethod.DELETE,
                null,
                JSONObject.class
            );

        Assertions.assertTrue(response.getStatusCode() == HttpStatus.NO_CONTENT);
    }

    @Test
	void deleteMethodShouldReturnNotFoundError() {
        ResponseEntity<JSONObject> response =
            this.restTemplate.exchange(
                this.getURI("/99"),
                HttpMethod.DELETE,
                null,
                JSONObject.class
            );

        Assertions.assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND);
    }


    private String getURI(String route) {
        return "http://localhost:" +
            this.port + this.contextPath +
            this.SERVICE_PATH + route ;
    }
}
