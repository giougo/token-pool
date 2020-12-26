package br.com.token.pool.core.service.expiration;

import br.com.token.pool.core.model.token.Token;
import br.com.token.pool.core.model.token.TokenExpirationStatus;

public interface TokenExpirationService {

	TokenExpirationStatus verifyTokenExpirationStatus(Token token);
}
