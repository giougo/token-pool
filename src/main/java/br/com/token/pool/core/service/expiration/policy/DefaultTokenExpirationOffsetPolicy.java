package br.com.token.pool.core.service.expiration.policy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.token.pool.core.model.Token;
import br.com.token.pool.core.model.TokenExpiration;

@Component
public class DefaultTokenExpirationOffsetPolicy implements TokenExpirationOffsetPolicy {

	@Value("${:50}")
	private Long tokenMillisecondsExpirationOffset;
	
	public TokenExpiration applyExpirationOffsetPolicy(Token token) {
	
		return TokenExpiration.from(token, tokenMillisecondsExpirationOffset);
	}
}
