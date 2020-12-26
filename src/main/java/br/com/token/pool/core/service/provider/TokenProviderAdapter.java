package br.com.token.pool.core.service.provider;

import java.util.Optional;

import br.com.token.pool.core.model.token.Token;

public interface TokenProviderAdapter {

	Optional<Token> retrieveToken();
}
