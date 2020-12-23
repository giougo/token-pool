package br.com.token.pool.service.refresher;

import br.com.token.pool.model.Token;

public interface TokenRefresher {

	Token refreshToken(Token token);
}
