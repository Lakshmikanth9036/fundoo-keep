package com.bridgelabz.fundookeep.service;

import com.bridgelabz.fundookeep.dao.User;
import com.bridgelabz.fundookeep.dto.LoginDTO;
import com.bridgelabz.fundookeep.dto.RegistrationDTO;

public interface UserService {

	public void saveUser(RegistrationDTO register);
	public int updateVerificationStatus(Long id);
	public User loginByEmail(LoginDTO login);
	public User loginByMobile(LoginDTO login);
}
