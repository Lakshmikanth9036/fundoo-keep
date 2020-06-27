package com.bridgelabz.fundookeep.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SmsDTO {

	private String phoneNumber;
	private String message;
	
}
