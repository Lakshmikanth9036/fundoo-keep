package com.bridgelabz.fundookeep.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

@Configuration
public class TwilioConfig {
	
	@Value("${account_sid}")
	private static String accountSid;
	
	@Value("${auth_token}")
	private static String authToken;
	
	static {
		Twilio.init(accountSid, authToken);
	}
}

