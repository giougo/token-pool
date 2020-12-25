package br.com.token.pool.core.service.expiration;

import br.com.token.pool.core.model.Token;
import br.com.token.pool.core.model.TokenExpirationStatus;

public interface TokenExpirationService {

	TokenExpirationStatus verifyTokenExpirationStatus(Token token);
}
