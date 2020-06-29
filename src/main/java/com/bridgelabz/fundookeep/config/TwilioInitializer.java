package com.bridgelabz.fundookeep.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

@Configuration
public class TwilioInitializer {

	private final TwilioConfig twilioConfig;
	
	@Autowired
	public TwilioInitializer(TwilioConfig twilioConfig) {
		this.twilioConfig = twilioConfig;
		Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
	}
}
