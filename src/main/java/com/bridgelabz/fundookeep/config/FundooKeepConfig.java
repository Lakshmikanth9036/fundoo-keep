package com.bridgelabz.fundookeep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bridgelabz.fundookeep.repository.UserRepositoryService;
import com.bridgelabz.fundookeep.utils.JwtUtils;
import com.bridgelabz.fundookeep.utils.MailService;

@Configuration
public class FundooKeepConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserRepositoryService getUserRepository() {
		return new UserRepositoryService();
	}
	
	@Bean
	public MailService getMailService() {
		return new MailService();
	}
	
	@Bean
	public JwtUtils getJwtUtils() {
		return new JwtUtils();
	}
}

