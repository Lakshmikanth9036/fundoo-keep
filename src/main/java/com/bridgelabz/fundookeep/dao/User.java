package com.bridgelabz.fundookeep.dao;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.bridgelabz.fundookeep.dto.RegistrationDTO;

@Entity
public class User {
	@Id
	@GenericGenerator(name = "idGen", strategy = "increment")
	@GeneratedValue(generator = "idGen")
	@Column(name = "UserID", nullable = false)
	private Long id;

	@Column(name = "FirstName", nullable = false)
	private String firstName;

	@Column(name = "LastName", nullable = false)
	private String lastName;

	@Column(name = "Email", nullable = false, unique = true)
	private String emailAddress;

	@Column(name = "MobileNo", nullable = false, unique = true)
	private Long mobile;

	@Column(name = "Password", nullable = false)
	private String password;

	@Column(name = "isUserVerified", nullable = false, columnDefinition = "bit(1) default 0")
	private boolean isUserVerified;

	@Column(name = "CreatedUserAt", nullable = false)
	private LocalDateTime createUser;

	@Column(name = "UserCreatedBy", nullable = false, columnDefinition = "varchar(10) default \"USER\"")
	private String userCreatedBy;

	@Column(name = "UserUpdatedAt", nullable = false)
	private LocalDateTime updateUser;

	@Column(name = "UserUpdatedBy", nullable = false, columnDefinition = "varchar(10) default \"USER\"")
	private String userUpdatedBy;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(RegistrationDTO register) {
//		super();
		this.firstName = register.getFirstName();
		this.lastName = register.getLastName();
		this.emailAddress = register.getEmailAddress();
		this.mobile = register.getMobile();
		this.password = register.getPassword();
		this.createUser = LocalDateTime.now();
		this.updateUser = LocalDateTime.now();
		this.userCreatedBy = "USER";
		this.userUpdatedBy = "USER";
		this.isUserVerified = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDateTime getCreateUser() {
		return createUser;
	}

	public void setCreateUser(LocalDateTime createUser) {
		this.createUser = createUser;
	}

	public String getUserCreatedBy() {
		return userCreatedBy;
	}

	public void setUserCreatedBy(String userCreatedBy) {
		this.userCreatedBy = userCreatedBy;
	}

	public LocalDateTime getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(LocalDateTime updateUser) {
		this.updateUser = updateUser;
	}

	public String getUserUpdatedBy() {
		return userUpdatedBy;
	}

	public void setUserUpdatedBy(String userUpdatedBy) {
		this.userUpdatedBy = userUpdatedBy;
	}

	public boolean isUserVerified() {
		return isUserVerified;
	}

	public void setUserVerified(boolean isUserVerified) {
		this.isUserVerified = isUserVerified;
	}

}
