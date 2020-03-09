package com.bridgelabz.fundookeep.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.bridgelabz.fundookeep.constants.Constants;
import com.bridgelabz.fundookeep.dao.User;


public class MailService {
	
	@Autowired
	private JavaMailSender mailSender;

	public void sendMail(User user,String token) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(user.getEmailAddress());
		message.setSubject(Constants.REGISTRATION_STATUS);
		message.setText("Hi " + user.getFirstName() + " "+user.getLastName()+ Constants.REGISTRATION_MESSAGE + Constants.VERIFICATION_LINK
				+ token);
		mailSender.send(message);
	}
	
	public void sendTokenToMail(String  token,String emailAddress) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(emailAddress);
		message.setSubject("Registration Confirmation");
		message.setText(Constants.VERIFICATION_LINK+token);
		mailSender.send(message);
	}
	
	
	
}
