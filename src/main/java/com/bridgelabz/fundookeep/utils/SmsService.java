package com.bridgelabz.fundookeep.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundookeep.config.TwilioConfig;
import com.bridgelabz.fundookeep.dto.SmsDTO;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {
	
	@Autowired
	private TwilioConfig twilioConfig;

	public void sendSms(SmsDTO sms) {
		
		PhoneNumber to = new PhoneNumber(sms.getPhoneNumber());
		PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
		String message = sms.getMessage();
		
		MessageCreator creator = Message.creator(to, from, message);
		creator.create();
	}

}
