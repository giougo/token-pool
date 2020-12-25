package br.com.token.pool.core.service.expiration;

import static br.com.token.pool.core.service.validator.TokenConstraintValidator.IS_ACCESS_TOKEN_PRESENT;
import static br.com.token.pool.core.service.validator.TokenConstraintValidator.IS_EXPIRATION_TIMESTAMP_PRESENT;
import static br.com.token.pool.core.service.validator.TokenConstraintValidator.IS_TOKEN_PRESENT;
import static br.com.token.pool.core.service.validator.TokenValidationResult.SUCCESS;

import org.springframework.stereotype.Component;

import br.com.token.pool.core.model.Token;
import br.com.token.pool.core.model.TokenExpiration;
import br.com.token.pool.core.model.TokenExpirationStatus;
import br.com.token.pool.core.service.expiration.policy.TokenExpirationOffsetPolicy;
import br.com.token.pool.core.service.validator.TokenValidationResult;

@Component
public class TokenOffsetableExpirationService implements TokenExpirationService {
	
	private TokenExpirationOffsetPolicy expirationOffsetPolicy;
	
	public TokenOffsetableExpirationService(TokenExpirationOffsetPolicy expirationOffsetPolicy) {
		
		this.expirationOffsetPolicy = expirationOffsetPolicy;
	}

	public TokenExpirationStatus verifyTokenExpirationStatus(Token token) {

		TokenValidationResult validationResult = validateTokenProperties(token);
		
		if (SUCCESS != validationResult) {
			
			// TODO CUSTOM EXCEPTION
			throw new RuntimeException();
		}
		
		TokenExpiration tokenExpiration = expirationOffsetPolicy.applyExpirationOffsetPolicy(token);
		
		return tokenExpiration.getExpirationStatus();
	}

	private TokenValidationResult validateTokenProperties(Token token) {
		
		return IS_TOKEN_PRESENT
			.and(IS_ACCESS_TOKEN_PRESENT)
			.and(IS_EXPIRATION_TIMESTAMP_PRESENT)
			.apply(token);
	}
}
