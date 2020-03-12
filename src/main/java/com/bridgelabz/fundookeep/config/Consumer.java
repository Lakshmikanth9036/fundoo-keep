package com.bridgelabz.fundookeep.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundookeep.constants.Constants;
import com.bridgelabz.fundookeep.dto.Mail;
import com.bridgelabz.fundookeep.utils.MailService;

@Component
public class Consumer {

	@Autowired
	private MailService mailSender;
	
	@RabbitListener(queues = Constants.QUEUE_NAME)
	public void receiveMail(Mail mail) {
		mailSender.sendMail(mail);
	}
	
}
