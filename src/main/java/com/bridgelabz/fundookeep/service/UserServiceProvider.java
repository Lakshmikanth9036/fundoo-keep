package com.bridgelabz.fundookeep.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundookeep.dao.User;
import com.bridgelabz.fundookeep.dto.LoginDTO;
import com.bridgelabz.fundookeep.dto.RegistrationDTO;
import com.bridgelabz.fundookeep.mailservice.MailService;
import com.bridgelabz.fundookeep.repository.UserRepository;

@Service
public class UserServiceProvider implements UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private MailService ms;

	@Autowired
	private PasswordEncoder encoder;

	public void saveUser(RegistrationDTO register) {
		register.setPassword(encoder.encode(register.getPassword()));
		User user = new User(register);
		
		User usr = repository.save(user);
		if(usr != null) {
			ms.sendMail(user);
		}
	}
	
	public int updateVerificationStatus(Long id) {
		return repository.updateUserVerificationStatus(id);
	}
	
	public User loginByEmail(LoginDTO login) {
		User user = repository.findByEmailAddress(login.getEmailAddress());
		if(user.isUserVerified() && user != null) {
			if(encoder.matches(login.getPassword(), user.getPassword())) {
				return user;
			}
		}
		return null;
	}
	
	public User loginByMobile(LoginDTO login) {
		User user = repository.findByMobile(login.getMobile());
		if(user.isUserVerified() && user != null) {
			if(encoder.matches(login.getPassword(), user.getPassword())) {
				return user;
			}
		}
		return null;
	}
}
