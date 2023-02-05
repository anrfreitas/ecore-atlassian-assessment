package com.store.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data // Generate get-setters automatically :)
@Entity(name="Customer")
@Table(name="customer")
public class Customer implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;

	@Column(name="name", nullable = false)
	@NotBlank(message="Type a name")
	private String name;

	@Column(name="email", nullable = false, unique = true)
	@Email(message="Type a valid email")
	private String email;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="customer")
    private List<Telephone> telephones;

	// @REFERENCE
	// description: not valid for the current structure, but leaving it here just as an example

	// @OneToMany(targetEntity = Telephone.class, cascade = CascadeType.ALL)
	// @JoinColumn(name = "customer_id", referencedColumnName = "id") // name = table.fk_name, referencedColumnName = currentTable.pk_name
	// private Set<Telephone> telephone;

}