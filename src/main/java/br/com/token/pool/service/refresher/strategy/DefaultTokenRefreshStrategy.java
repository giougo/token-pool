package br.com.token.pool.service.refresher.strategy;

import br.com.token.pool.model.Token;
import br.com.token.pool.service.provider.TokenProvider;
import br.com.token.pool.service.refresher.TokenRefresher;

public class DefaultTokenRefreshStrategy implements TokenRefresher {
	
	private TokenProvider tokenProvider;

	@Override
	public Token refreshToken(Token token) {
		
		return tokenProvider.retrieveToken()
				.orElseThrow();
	}
}
