package com.bridgelabz.fundookeep.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@ConfigurationProperties("twilio")
@NoArgsConstructor
@Data
public class TwilioConfig {
	
	private String accountSid;
	
	private String authToken;
	
	private String trialNumber;
	
}

