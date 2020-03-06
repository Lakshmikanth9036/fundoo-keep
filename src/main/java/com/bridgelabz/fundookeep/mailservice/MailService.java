package com.bridgelabz.fundookeep.mailservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.bridgelabz.fundookeep.dao.User;


public class MailService {
	
	@Autowired
	private JavaMailSender mailSender;

	public void sendMail(User user) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(user.getEmailAddress());
		message.setSubject("Registration Confirmation");
		message.setText("Hi " + user.getFirstName() + " "+user.getLastName()+", you have successfully Registrered to our website\n"
				+ "please click on below link to verify:\n" + "http://localhost:8080/user/registration/verify/"
				+ user.getId());
		mailSender.send(message);
	}
	
}
