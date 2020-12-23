package br.com.token.pool.service.manager;

import br.com.token.pool.model.Token;
import br.com.token.pool.service.manager.state.TokenManagerState;

public interface TokenManager {

	Token getToken();
	void refreshToken();
	TokenManagerState getState();
}
