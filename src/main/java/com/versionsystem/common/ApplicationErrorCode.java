package com.versionsystem.common;

public class ApplicationErrorCode {
	
	private  String code="";    
	private  String message;    
	
	
	public ApplicationErrorCode(String code,String message){    
		this.code=code;    
		this.message=message;    
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String toString(){
		return this.getCode()+"|"+this.getMessage();
	}

}
