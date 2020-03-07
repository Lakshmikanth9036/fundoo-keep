package com.bridgelabz.fundookeep.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class LoginDTO {

	@NotNull(message = "Last Name field can't be empty!!!")
	@Email(message = "Enter valid mail address!!!")
	private String emailAddress;
	
	
	private Long mobile;
	private String password;

	public LoginDTO() {
		// TODO Auto-generated constructor stub
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
