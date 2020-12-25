package br.com.token.pool.core.service.manager;

import static br.com.token.pool.core.model.TokenManagerState.FETCHING_TOKEN;
import static br.com.token.pool.core.model.TokenManagerState.TOKEN_NOT_STORED;
import static br.com.token.pool.core.model.TokenManagerState.TOKEN_STORED;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.token.pool.core.model.Token;
import br.com.token.pool.core.model.TokenManagerState;
import br.com.token.pool.core.service.refresher.TokenRefresher;

// TODO IMPLEMENTAR POLICY PARA REFRESH DE TOKEN (TEMPO EXPIRACAO / NULL)
// TODO IMPLEMENTAR THREAD SAFETY
// TODO CONSIDERAR MAIOR ROBUSTEZ PARA STATE

@Component
@Scope(value = SCOPE_PROTOTYPE)
public class DefaultTokenManager implements TokenManager {
	
	private TokenRefresher tokenRefresher;
	
	private TokenManagerState managerState;
	
	private Token token;

	public DefaultTokenManager(TokenRefresher tokenRefresher) {
		
		this.tokenRefresher = tokenRefresher;
		this.managerState = TOKEN_NOT_STORED;
	}

	public Token getToken() {
		
		boolean tokenIsNotStored = managerState == TOKEN_NOT_STORED;
		
		if (tokenIsNotStored) {
			
			refreshToken();
		}
		
		return pluckToken();
	}
	
	private Token pluckToken() {
		
		Token token = this.token;
		
		changeStateTo(TOKEN_NOT_STORED);
		
		this.token = null;
		
		return token;
	}

	public void refreshToken() {
		
		TokenManagerState formerState = getState();
		
		changeStateTo(FETCHING_TOKEN);
		
		try {
			
			token = tokenRefresher.refreshToken(token);
			
			changeStateTo(TOKEN_STORED);
		
		} catch (Exception e) {
			
			changeStateTo(formerState);
		}
		
	}
	
	public TokenManagerState getState() {
		
		return managerState;
	}
	
	private void changeStateTo(TokenManagerState state) {
		
		this.managerState = state;
	}
}
