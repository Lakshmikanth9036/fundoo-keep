package com.bridgelabz.fundookeep.dto;

import javax.validation.constraints.NotNull;

public class RegistrationDTO {

	@NotNull(message = "Enter FName")
	private String firstName;
	@NotNull(message = "Enter LName")
	private String lastName;
	@NotNull(message = "Enter Email")
	private String emailAddress;
	@NotNull(message = "Enter mobile")
	private Long mobile;
	@NotNull(message = "Enter pass")
	private String password;

	public RegistrationDTO() {
		// Auto-generated constructor stub
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
