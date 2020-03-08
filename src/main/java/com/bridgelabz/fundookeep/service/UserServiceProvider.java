package com.bridgelabz.fundookeep.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundookeep.dao.User;
import com.bridgelabz.fundookeep.dto.LoginDTO;
import com.bridgelabz.fundookeep.dto.RegistrationDTO;
import com.bridgelabz.fundookeep.dto.UserResponse;
import com.bridgelabz.fundookeep.exception.UserException;
import com.bridgelabz.fundookeep.repository.UserRepository;
import com.bridgelabz.fundookeep.utils.JwtUtils;
import com.bridgelabz.fundookeep.utils.MailService;

@Service
@PropertySource("classpath:message.properties")	
public class UserServiceProvider implements UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private MailService mailService;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtUtils jwts;

	@Autowired
	private Environment env;
	
	/**
	 * Saves the user details 
	 */
	@Override
	public void saveUser(RegistrationDTO register) {

		if (repository.findByEmailAddress(register.getEmailAddress()).isPresent()) 
			throw new UserException(HttpStatus.FOUND.value(),env.getProperty("103"));
		
		register.setPassword(encoder.encode(register.getPassword()));
		User user = new User(register);
		try {
			User usr = repository.save(user);
			if (usr != null) {
				mailService.sendMail(user, jwts.generateToken(user.getUserId()));
			}
		} catch (UserException e) {
			throw new UserException(400,env.getProperty("102"));
		}
	}
	
	/**
	 *Update the user verification status if the received token is valid
	 *@return integer value that is number of record that had been updated  
	 */
	@Override
	public  int updateVerificationStatus(String token) {
		Long id = jwts.decodeToken(token);
		try {
			return repository.updateUserVerificationStatus(id,LocalDateTime.now());
		} catch (UserException e) {
			throw new UserException(400,env.getProperty("102"));
		}
	}

	/**
	 * Login to the application using login credential
	 * @return user details which is necessary
	 */
	@Override
	public UserResponse loginByEmailOrMobile(LoginDTO login) {

		User user = repository.findByEmailAddressOrMobile(login.getEmailAddress(), login.getMobile())
				.orElseThrow(() -> new UserException(404,env.getProperty("104")));
		if (user.isUserVerified()) {
			if (encoder.matches(login.getPassword(), user.getPassword())) {
				return new UserResponse(user);
			}
			throw new UserException(401,env.getProperty("401"));
		}
		return null;
	}

	/**
	 * Send the jwt token to the user mail
	 */
	@Override
	public void sendTokentoMail(String emailAddress) {

		User user = repository.findByEmailAddress(emailAddress)
				.orElseThrow(() -> new UserException(404,env.getProperty("104")));
		String token = jwts.generateToken(user.getUserId());
		mailService.sendTokenToMail(token, emailAddress);

	}

	/**
	 * Provides the link to reset the password to user mail address
	 */
	@Override
	public int resetPassword(String token, String newPassword) {
		Long id = jwts.decodeToken(token);
			return repository.updatePassword(id, encoder.encode(newPassword), LocalDateTime.now());
	}

}
