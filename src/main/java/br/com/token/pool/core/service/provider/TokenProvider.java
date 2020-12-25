package br.com.token.pool.core.service.provider;

import java.util.Optional;

import br.com.token.pool.core.model.Token;

public interface TokenProvider {

	Optional<Token> retrieveToken();
}
