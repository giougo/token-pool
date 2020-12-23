package br.com.token.pool.service.provider;

import java.util.Optional;

import br.com.token.pool.model.Token;

public interface TokenProvider {

	Optional<Token> retrieveToken();
}
