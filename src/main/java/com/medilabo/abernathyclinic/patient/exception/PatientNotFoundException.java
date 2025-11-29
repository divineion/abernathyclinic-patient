package com.medilabo.abernathyclinic.patient.exception;

@SuppressWarnings("serial")
public class PatientNotFoundException extends Exception {
	public PatientNotFoundException(String message) {
		super(message);
	}
}
