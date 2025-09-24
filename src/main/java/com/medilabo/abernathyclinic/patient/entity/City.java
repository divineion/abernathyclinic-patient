package com.medilabo.abernathyclinic.patient.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="city")
public class City {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(length=50, nullable=false)
	private String name;
	
	@Column(length=5, nullable=false)
	private String zip;

	public City() {}

	public City(String name, String zip) {
		this.name = name;
		this.zip = zip;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append(this.zip)
				.append(" ")
				.append(this.name)
				.toString();
	}
}
