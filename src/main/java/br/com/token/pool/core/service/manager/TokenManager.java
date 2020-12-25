package br.com.token.pool.core.service.manager;

import br.com.token.pool.core.model.Token;
import br.com.token.pool.core.model.TokenManagerState;

public interface TokenManager {

	Token getToken();
	void refreshToken();
	TokenManagerState getState();
}
