package com.code.mobilewsrestapi.ui.model.response.error;

public enum ErrorMessages {
	
	MISSING_REQUIRED_FIELD("Missing required Fields. Please check documnetations."),
	RECORD_ALREDAY_EXITS("Record Already Exits"),
	INVALID_HTTP_METHOD("Invalid Http Request Method"),
	INTERNAL_SERVER_ERROR("Internal Server Error"),
	VALIDATION_FAILED("Valdidation Falied"),
	COULD_UPDATE_RECORD("Could not update record"),
	COULD_DELETE_RECORD("Could not  delete record"),
	MALFORMED_JAOSN_REQUEST("Malformed Json Request"),
	MISSING_REQUIRED_PARMETER("Parameter Missing."),
	UNSUPPORTED_MEDIAT_TYPE("Media type is not supported. Supported media tyeps are");
	
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	private ErrorMessages(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

}
