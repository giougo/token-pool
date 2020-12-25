package br.com.token.pool.core.service.validator;

import static br.com.token.pool.core.service.validator.TokenValidationResult.ABSENT_ACCESS_TOKEN;
import static br.com.token.pool.core.service.validator.TokenValidationResult.ABSENT_EXPIRATION_TIME;
import static br.com.token.pool.core.service.validator.TokenValidationResult.ABSENT_EXPIRATION_TIMESTAMP;
import static br.com.token.pool.core.service.validator.TokenValidationResult.ABSENT_TOKEN;
import static br.com.token.pool.core.service.validator.TokenValidationResult.EXPIRED_TOKEN;
import static br.com.token.pool.core.service.validator.TokenValidationResult.SUCCESS;
import static br.com.token.pool.core.utils.TimeUtils.getNowTimestamp;

import java.util.function.Function;

import br.com.token.pool.core.model.Token;

public interface TokenConstraintValidator extends Function<Token, TokenValidationResult> {

    TokenConstraintValidator IS_TOKEN_PRESENT = token -> token == null ? SUCCESS : ABSENT_TOKEN;

    TokenConstraintValidator IS_EXPIRATION_AMOUNT_PRESENT = token -> token.expiresInMilliseconds() == 0 ? SUCCESS : ABSENT_EXPIRATION_TIME;
    
    TokenConstraintValidator IS_EXPIRATION_TIMESTAMP_PRESENT = token -> token.expirationTimestamp() != null ? SUCCESS : ABSENT_EXPIRATION_TIMESTAMP;

    TokenConstraintValidator IS_ACCESS_TOKEN_PRESENT = token -> {

    	String accessToken = token.accessToken();
    	
    	if (accessToken == null || accessToken.isBlank()) {
    		
    		return ABSENT_ACCESS_TOKEN;
    	}
    	
    	return SUCCESS;
    };
    
    TokenConstraintValidator IS_TOKEN_EXPIRED = (token) -> {
		
		Long tokenExpirationTimestamp = token.expirationTimestamp();
		
		if (tokenExpirationTimestamp - getNowTimestamp() <= 0) {
			
			return EXPIRED_TOKEN;
		}
		
		return SUCCESS;
	};
    
    default TokenConstraintValidator and(TokenConstraintValidator other) {
    	
        return token -> {
        	
        	TokenValidationResult result = this.apply(token);
        	
        	if (SUCCESS == result) {
        		
        		return other.apply(token);
        	}
        	
        	return result;
        };
    }
}
