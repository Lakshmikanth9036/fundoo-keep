package com.bridgelabz.fundookeep.exception;

public class UserException extends RuntimeException{

	private int errorCode;
	private String errorMsg;
	
	public UserException(int errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	
}
