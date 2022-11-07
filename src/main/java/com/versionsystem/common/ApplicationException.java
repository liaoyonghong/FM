package com.versionsystem.common;

public class ApplicationException extends RuntimeException {

    public static final ApplicationException NOT_FIND = new ApplicationException("Object Not Find");
    public static final ApplicationException PARAM_ERROR = new ApplicationException("Request Param Error");
    public static final ApplicationException VALIDATION_ERROR = new ApplicationException(new ApplicationErrorCode("APP3001", "Validation Error").toString());
    public static final ApplicationException DUPLICATED_OBJECT = new ApplicationException(new ApplicationErrorCode("APP3001", "Validation Error:Failed to add new record!Duplicated Object!").toString());

    public static ApplicationException UNEXPECTED_CHARGE(Object type) {
        return new ApplicationException("Unexpected Charge Type(PRESCRIPTION/CONSUME/LAB/OTHERS):" + type);
    }

    public static ApplicationException NOT_FIND(Object msg) {
        return new ApplicationException("Data Loss:" + msg);
    }
    
    public static ApplicationException CATCH_THROW(Exception e) {
		String errorMsg = "";
		if(e.getCause().getCause() != null){
			errorMsg = e.getCause().getCause().getMessage();
		}else if(e.getCause() != null){
			errorMsg = e.getCause().getMessage();
		}else{
			if(e.getMessage() != null){
				errorMsg = e.getMessage();
			}else{
				errorMsg = e.toString();
			}
		}
        return new ApplicationException(new ApplicationErrorCode(errorMsg,errorMsg).toString());
    }

	public ApplicationException(String msg) {
		super(msg);
	}

	public ApplicationException(Exception exception) {
		super(exception.getMessage());
	}

}
