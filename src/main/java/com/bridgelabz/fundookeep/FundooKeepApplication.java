package com.bridgelabz.fundookeep;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
//@ComponentScan(basePackages = {"com.bridgelabz.fundookeep"})
public class FundooKeepApplication {

	static {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	    System.setProperty("log.created.at", dateFormat.format(new Date()));
	} 
	
	public static void main(String[] args) {
		SpringApplication.run(FundooKeepApplication.class, args);
	}
	
	@Bean
	public Docket swaggerConfiguration() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.bridgelabz.fundookeep.controller"))
				.build()
				;
	}

}
