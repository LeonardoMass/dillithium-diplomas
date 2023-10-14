package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Dilithium {
	@Id
	@GeneratedValue
	@Column(name = "dilithiumID")
	private Long dilithiumID;
	public Dilithium() {
	}

	public long getId() {
		return this.dilithiumID;
	}

	public void setId(long id) {
		this.dilithiumID = id;
	}
}
