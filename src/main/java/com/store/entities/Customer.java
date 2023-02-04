package com.store.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data // Generate get-setters automatically :)
@Entity(name="Customer")
@Table(name="Customer")
public class Customer implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;

	@Column(name="name", nullable = false)
	@NotBlank(message="Type a name")
	private String name;

	@Column(name="email", nullable = false, unique = true)
	@Email(message="Type a valid email")
	private String email;

	@Column(name="telephone", nullable = true)
	private String telephone;

}