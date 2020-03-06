package com.bridgelabz.fundookeep.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.bridgelabz.fundookeep.dao.User;


public class MailService {
	
	@Autowired
	private JavaMailSender mailSender;

	public void sendMail(User user,String token) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(user.getEmailAddress());
		message.setSubject("Registration Confirmation");
		message.setText("Hi " + user.getFirstName() + " "+user.getLastName()+", you have successfully Registrered to our website\n"
				+ "please click on below link to verify:\n" + "http://localhost:8080/user/registration/verify/"
				+ token);
		mailSender.send(message);
	}
	
	public void sendTokenToMail(String  token,String emailAddress) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(emailAddress);
		message.setSubject("Registration Confirmation");
		message.setText("Using below link reset your password\n"+"http://localhost:8080/user/login/forgotpassword/"+token);
		mailSender.send(message);
	}
	
	
	
}
