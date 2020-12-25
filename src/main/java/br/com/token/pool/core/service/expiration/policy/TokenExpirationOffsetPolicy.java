package br.com.token.pool.core.service.expiration.policy;

import br.com.token.pool.core.model.Token;
import br.com.token.pool.core.model.TokenExpiration;

public interface TokenExpirationOffsetPolicy {

	 TokenExpiration applyExpirationOffsetPolicy(Token token);
}
