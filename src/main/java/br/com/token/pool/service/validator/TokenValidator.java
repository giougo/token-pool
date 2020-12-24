package br.com.token.pool.service.validator;

import static br.com.token.pool.service.validator.TokenValidationResult.ABSENT_ACCESS_TOKEN;
import static br.com.token.pool.service.validator.TokenValidationResult.ABSENT_EXPIRATION_TIME;
import static br.com.token.pool.service.validator.TokenValidationResult.ABSENT_TOKEN;
import static br.com.token.pool.service.validator.TokenValidationResult.VALID_TOKEN;

import java.util.EnumSet;
import java.util.function.Function;

import br.com.token.pool.model.Token;

public interface TokenValidator extends Function<Token, EnumSet<TokenValidationResult>> {

    EnumSet<TokenValidationResult> SUCCESS = EnumSet.of(VALID_TOKEN);

    TokenValidator IS_TOKEN_ABSENT = token -> token == null ? SUCCESS : EnumSet.of(ABSENT_TOKEN);

    TokenValidator IS_EXPIRATION_ABSENT = token -> token.expiresInMilliseconds() == 0 ? SUCCESS : EnumSet.of(ABSENT_EXPIRATION_TIME);
    
    TokenValidator IS_ACCESS_TOKEN_ABSENT = token -> {
    	
    	if (token == null) {
    		return EnumSet.of(ABSENT_ACCESS_TOKEN);
    	}
    	
    	String accessToken = token.accessToken();
    	
    	if (accessToken == null || accessToken.isBlank()) {
    		return EnumSet.of(ABSENT_ACCESS_TOKEN);
    	}
    	
    	return SUCCESS;
    };

    default TokenValidator and(TokenValidator other) {
        
    	return token -> {
    		
            EnumSet<TokenValidationResult> thisResult = this.apply(token);
            
            EnumSet<TokenValidationResult> otherResult = other.apply(token);
            
            if (thisResult.equals(SUCCESS)) {
            	
            	return otherResult;
            }
            
            if (otherResult.equals(SUCCESS)) {
            	
            	return thisResult;
            }
            
            EnumSet<TokenValidationResult> combinedResult = EnumSet.copyOf(thisResult);
            
            combinedResult.addAll(otherResult);
            
            return combinedResult;
        };
    }
}
