package com.bridgelabz.fundookeep;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FundooKeepApplication {
	
	static {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	    System.setProperty("log.created.at", dateFormat.format(new Date()));
	} 
	
	public static void main(String[] args) {
		SpringApplication.run(FundooKeepApplication.class, args);
	}

}
