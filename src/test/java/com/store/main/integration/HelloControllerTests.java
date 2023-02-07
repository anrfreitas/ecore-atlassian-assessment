package com.store.main.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import net.minidev.json.JSONObject;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HelloControllerTests {

    @Value("${local.server.port}")
	private String port;

    @Value("${server.servlet.contextPath}")
	private String contextPath;

    private final String SERVICE_PATH = "/hello";

    @Autowired
	private TestRestTemplate restTemplate;

    @Test
	void getMethodShouldReturnHelloWorld() {
        ResponseEntity<String> response =
            this.restTemplate.getForEntity(
                this.getURI("/world"),
                String.class
            );

        Assertions.assertTrue(response.getStatusCode() == HttpStatus.OK);
        Assertions.assertTrue(response.getBody().contains("It works!"));
    }

    @Test
    void postMethodShouldReturnPayloadAsResponseBody() {
        JSONObject payload = new JSONObject();
        payload.put("name", "andre");
        payload.put("email", "andre@email.com");

        ResponseEntity<JSONObject> response =
            this.restTemplate.postForEntity(
                this.getURI("/world"),
                payload,
                JSONObject.class
            );

        Assertions.assertTrue(response.getStatusCode() == HttpStatus.OK);
        Assertions.assertEquals(payload.get("name"), response.getBody().get("name"));
        Assertions.assertEquals(payload.get("email"), response.getBody().get("email"));

    }

    @Test
    void postMethodShouldReturnValidationError() {
        JSONObject payload = new JSONObject();
        // payload.put("name", "andre");
        payload.put("email", "andre@email.com");

        ResponseEntity<JSONObject> response =
            this.restTemplate.postForEntity(
                this.getURI("/world"),
                payload,
                JSONObject.class
            );

        Assertions.assertTrue(response.getStatusCode() == HttpStatus.BAD_REQUEST);
    }

    private String getURI(String route) {
        return "http://localhost:" +
            this.port + this.contextPath +
            this.SERVICE_PATH + route ;
    }
}
