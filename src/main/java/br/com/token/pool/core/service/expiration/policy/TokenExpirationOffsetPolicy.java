package br.com.token.pool.core.service.expiration.policy;

import br.com.token.pool.core.model.token.Token;
import br.com.token.pool.core.model.token.TokenExpiration;

public interface TokenExpirationOffsetPolicy {

	 TokenExpiration applyExpirationOffsetPolicy(Token token);
}
