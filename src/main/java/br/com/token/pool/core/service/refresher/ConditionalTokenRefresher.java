package br.com.token.pool.core.service.refresher;

import static br.com.token.pool.core.model.token.TokenExpirationStatus.NON_EXPIRABLE;
import static br.com.token.pool.core.model.token.TokenExpirationStatus.NOT_EXPIRED;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.token.pool.core.model.token.Token;
import br.com.token.pool.core.model.token.TokenExpirationStatus;
import br.com.token.pool.core.service.expiration.TokenExpirationService;
import br.com.token.pool.core.service.provider.TokenProviderAdapter;

@Component
public class ConditionalTokenRefresher implements TokenRefresher {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConditionalTokenRefresher.class);
	
	private static final String MESSAGE_TOKEN_NOT_EXPIRED = "The token with id < {} > is not expired. According to the conditional token refresh strategy, a token must be expired in order to be renewed. Returning the same input token";
	
	private TokenProviderAdapter tokenProvider;
	
	private TokenExpirationService tokenExpirationService;
	
	public ConditionalTokenRefresher(TokenProviderAdapter tokenProvider, TokenExpirationService tokenExpirationService) {
		
		this.tokenProvider = tokenProvider;
		
		this.tokenExpirationService = tokenExpirationService;
	}

	@Override
	public Token refreshToken(Token token) {
		
		TokenExpirationStatus expirationStatus = tokenExpirationService.verifyTokenExpirationStatus(token);
		
		if (NOT_EXPIRED == expirationStatus || NON_EXPIRABLE == expirationStatus) {
			
			LOGGER.info(MESSAGE_TOKEN_NOT_EXPIRED, token.getTokenId());
			
			return token;
		}
		
		// TODO CUSTOM EXCEPTION
		return refreshToken();
	}

	@Override
	public Token refreshToken() {
		
		// TODO CUSTOM EXCEPTION
		return requestToken()
			.orElseThrow();
	}
	
	private Optional<Token> requestToken() {
		
		return tokenProvider.retrieveToken();
	}
}
