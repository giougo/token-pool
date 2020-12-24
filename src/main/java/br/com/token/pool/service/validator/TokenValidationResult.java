package br.com.token.pool.service.validator;

public enum TokenValidationResult {
	VALID_TOKEN("The token is valid"),
	ABSENT_TOKEN("The token is absent"),
	ABSENT_ACCESS_TOKEN("The access token value is absent from the token"),
	ABSENT_EXPIRATION_TIME("The token expiration time is absent");
	
	private String validationDescription;

	private TokenValidationResult(String validationDescription) {
		this.validationDescription = validationDescription;
	}

	public String getValidationDescription() {
		return validationDescription;
	}
}
