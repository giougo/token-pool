package br.com.token.pool.core.service.refresher;

import br.com.token.pool.core.model.Token;

public interface TokenRefresher {

	Token refreshToken(Token token);
	Token refreshToken();
}
