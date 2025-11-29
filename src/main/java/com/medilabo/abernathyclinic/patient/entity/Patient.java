package com.medilabo.abernathyclinic.patient.entity;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@DynamicUpdate
@Entity
@Table(name = "patient")
public class Patient {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "UUID", nullable=false, unique=true, updatable=false)
	private UUID uuid;
	
	@Column(name="last_name", nullable=false, length=80)
	private String lastName;
	
	@Column(name="first_name", nullable=false, length=80)
	private String firstName;
	
	@Column(name="birth_date", nullable=false)
	private LocalDate birthDate;
	
	@Column(length=10, nullable=false)
	private String gender;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="address_id")
	private Address address;
	
	@Column(length=20)
	private String phone;

	public Patient() {}
	
	public Patient(String lastName, String firstName, LocalDate birthDate, String gender, Address address,
			String phone) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.birthDate = birthDate;
		this.gender = gender;
		this.address = address;
		this.phone = phone;
	}
	
	public Patient(UUID uuid, String lastName, String firstName, LocalDate birthDate, String gender, Address address,
			String phone) {
		this.uuid = uuid;
		this.lastName = lastName;
		this.firstName = firstName;
		this.birthDate = birthDate;
		this.gender = gender;
		this.address = address;
		this.phone = phone;
	}
	
	public Long getId() {
		return this.id;
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@PrePersist
    public void generateUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("Patient : id : ")
				.append(this.id)
				.append(", Lastname : ")
				.append(this.lastName)
				.append(", firstname : ")
				.append(this.firstName)
				.append(", birth date: ")
				.append(this.birthDate)
				.append(", gender: ")
				.append(this.gender)
				.toString();
	}
	
	public String toDebugString() {
		return new StringBuilder()
				.append("Patient : id: ")
				.append(this.id)
				.append(", uuid: ")
				.append(this.uuid)
				.append(", Lastname: ")
				.append(this.lastName)
				.append(", firstname: ")
				.append(this.firstName)
				.append(", birth date: ")
				.append(this.birthDate)
				.append(", gender: ")
				.append(this.gender)
				.toString();
	}
}
