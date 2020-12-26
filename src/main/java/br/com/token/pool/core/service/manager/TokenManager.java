package br.com.token.pool.core.service.manager;

import br.com.token.pool.core.model.token.Token;
import br.com.token.pool.core.model.token.TokenManagerState;

public interface TokenManager {

	Token getToken();
	TokenManagerState getState();
}
