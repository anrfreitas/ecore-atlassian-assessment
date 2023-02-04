package com.store.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class HelloWorldDTO {
	@NotBlank(message="Type a name")
	private String name;

	@Email(message="Type a valid email")
	private String email;
}
