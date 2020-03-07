package com.bridgelabz.fundookeep.service;

import com.bridgelabz.fundookeep.dto.LoginDTO;
import com.bridgelabz.fundookeep.dto.RegistrationDTO;

public interface UserService {

	public void saveUser(RegistrationDTO register);
	public int updateVerificationStatus(String token);
	public boolean loginByEmailOrMobile(LoginDTO login);
	public void sendTokentoMail(String emailAddress);
	public int resetPassword(String token, String newPassword);
}
