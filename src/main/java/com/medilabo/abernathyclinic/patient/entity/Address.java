package com.medilabo.abernathyclinic.patient.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="address")
public class Address {
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	private int id;
	
	@Column(name="street_number", length=5)
	private String streetNumber;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="street_id")
	private Street street;

	public Address() {}

	public Address(String streetNumber, Street street) {
		this.streetNumber = streetNumber;
		this.street = street;
	}

	public int getId() {
		return id;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public Street getStreet() {
		return street;
	}

	public void setStreet(Street street) {
		this.street = street;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append(this.streetNumber)
				.append(" ")
				.append(this.street)
				.toString();
	}
}
