package com.store.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data // Generate get-setters automatically :)
@Entity(name="CustomerTelephone")
@Table(name="customer_telephone")
public class Telephone implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;

	@JsonIgnore
	private long customer_id;

	@Column(name="phone", nullable = false)
	@NotBlank(message="Type a phone")
	private String phone;

	// @ManyToOne(targetEntity = Customer.class, cascade = CascadeType.ALL)
	// @JoinColumn(name = "customer_id")
	// private Customer customer;
}