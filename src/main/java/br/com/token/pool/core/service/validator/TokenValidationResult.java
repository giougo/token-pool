package br.com.token.pool.core.service.validator;

public enum TokenValidationResult {
	
	ABSENT_ACCESS_TOKEN("The access token value is absent from the token"),
	ABSENT_EXPIRATION_TIME("The token expiration time is absent"),
	ABSENT_TOKEN("The token is absent"),
	ABSENT_EXPIRATION_TIMESTAMP("The token expiration timestamp is absent"),
	EXPIRED_TOKEN("The token validity is expired"),
	SUCCESS("The token fits all constraint requirements");
	
	private String violationDescription;

	private TokenValidationResult(String validationDescription) {
		this.violationDescription = validationDescription;
	}

	public String getViolationDescription() {
		return violationDescription;
	}
}
