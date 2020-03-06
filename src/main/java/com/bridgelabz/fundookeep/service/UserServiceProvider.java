package com.bridgelabz.fundookeep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundookeep.dao.User;
import com.bridgelabz.fundookeep.dto.LoginDTO;
import com.bridgelabz.fundookeep.dto.RegistrationDTO;
import com.bridgelabz.fundookeep.repository.UserRepository;
import com.bridgelabz.fundookeep.utils.JwtUtils;
import com.bridgelabz.fundookeep.utils.MailService;

@Service
public class UserServiceProvider implements UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private MailService ms;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtUtils jwts;

	public void saveUser(RegistrationDTO register) {
		register.setPassword(encoder.encode(register.getPassword()));
		User user = new User(register);

		User usr = repository.save(user);
		if (usr != null) {
			ms.sendMail(user,jwts.generateToken(user.getId()));
		}
	}

	public int updateVerificationStatus(String token) {
		Long id = jwts.decodeToken(token);
		return repository.updateUserVerificationStatus(id);
	}

	public User loginByEmailOrMobile(LoginDTO login) {

		User user = repository.findByEmailAddressOrMobile(login.getEmailAddress(), login.getMobile());
		if (user.isUserVerified() && user != null) {
			if (encoder.matches(login.getPassword(), user.getPassword())) {
				return user;
			}
		}
		return null;
	}

	public void sendTokentoMail(String emailAddress) {

		User user = repository.findByEmailAddress(emailAddress);
		String token = jwts.generateToken(user.getId());
		ms.sendTokenToMail(token, emailAddress);

	}

	public int resetPassword(String token, String newPassword) {
		Long id = jwts.decodeToken(token);
		int status = repository.updatePassword(id, encoder.encode(newPassword));
		return status;
	}

}
