package com.versionsystem.common;

public enum ApplicationError {
	
	FileNotFoundException("APP1000","File not found"),
	ObjectNotFoundException("APP1001","Object not found"),
	NumberFormatedException("APP1002","Invalid number"),
	InvalidUserException("APP1003","Invalid user password"),
	IOException("APP10000","IO exception"),
	ParameterMappingException("APP1005","Drug mapping parameters do not match"),
	ReferencedByOthers("APP2001","ORA-02292:Data referenced by other datas!"),
	FailedGetData("APP2002","Failed to get data from remote!"),
	PasswordUnique("APP2003","Password must be unique!");
	private  String code="";
    private  String message;
    ApplicationError(String code,String message){
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
