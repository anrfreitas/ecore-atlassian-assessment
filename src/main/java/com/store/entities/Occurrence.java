package com.store.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data // Generate get-setters automatically :)
@Entity(name="Occurrence")
@Table(name="occurrence")
public class Occurrence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;

    @Column(name = "created_at")
    private Timestamp createdAt;

    public Occurrence() {}

    public Occurrence(Timestamp createdAt) {
        this.setCreatedAt(createdAt);
    }

}
