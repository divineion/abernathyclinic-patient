package com.medilabo.abernathyclinic.patient.exception;

public class PatientNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public PatientNotFoundException(String message) {
		super(message);
	}
}
