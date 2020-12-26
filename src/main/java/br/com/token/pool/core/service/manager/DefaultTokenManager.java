package br.com.token.pool.core.service.manager;

import static br.com.token.pool.core.model.token.TokenManagerState.FETCHING_TOKEN;
import static br.com.token.pool.core.model.token.TokenManagerState.TOKEN_NOT_STORED;
import static br.com.token.pool.core.model.token.TokenManagerState.TOKEN_STORED;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.util.concurrent.locks.Lock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.token.pool.core.model.token.Token;
import br.com.token.pool.core.model.token.TokenManagerState;
import br.com.token.pool.core.service.refresher.TokenRefresher;
import io.reactivex.Observable;

// TODO IMPLEMENTAR POLICY PARA REFRESH DE TOKEN (TEMPO EXPIRACAO / NULL)
// TODO IMPLEMENTAR THREAD SAFETY
// TODO CONSIDERAR MAIOR ROBUSTEZ PARA STATE

@Component
@Scope(value = SCOPE_PROTOTYPE)
public class DefaultTokenManager implements TokenManager {

	private TokenRefresher tokenRefresher;

	private TokenManagerState managerState;

	private Token token;

	private Lock lock;
	
	@Value("${}")
	private long refreshTimeoutMilliseconds;
	
	@Value("${}")
	private int refreshRetryAmounts;
	
	private Observable<Token> lazyTokenObservable = Observable
			.fromCallable(() -> tokenRefresher.refreshToken(token))
//			TODO IMPLEMENTAR CUSTOM THREAD
//			.subscribeOn(Schedulers.from(executor))
			.timeout(refreshTimeoutMilliseconds, MILLISECONDS)
			.retryWhen(
				failures -> failures.zipWith(
						Observable.range(1, refreshRetryAmounts),
						this::handleRetryAttempt
				)
				.flatMap(x -> x)
			);
	
	private Observable<Long> handleRetryAttempt(Throwable err, int attempt) {
		
		return null;
//		switch (attempt) {
//			
//			case 1:
//				return Observable.just(42L);
//				
//			case refreshRetryAmounts:
//				return Observable.error(err);
//			
//			default:
//				return Observable.timer(refreshTimeoutMilliseconds, MILLISECONDS);
//		}
	}

	public DefaultTokenManager(TokenRefresher tokenRefresher) {

		this.tokenRefresher = tokenRefresher;
		this.managerState = TOKEN_NOT_STORED;
	}

	public Token getToken() {

		if (tokenIsNotAvailable()) {

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

	private void refreshToken() {

		// TODO LOCK DE REFRESH OU DE GET TOKEN
		changeStateTo(FETCHING_TOKEN);
		
		lazyTokenObservable.blockingSubscribe(
			(Token token) -> this.token = token,
			// TODO HANDLE ERRORS
			(Throwable t) -> t.printStackTrace(),
			() -> changeStateTo(TOKEN_STORED)
		);
	}

	// METODOS UTILITARIOS

	public TokenManagerState getState() {

		return managerState;
	}

	private void changeStateTo(TokenManagerState state) {

		this.managerState = state;
	}

	private boolean tokenIsAvailable() {

		return managerState == TOKEN_STORED;
	}

	private boolean tokenIsNotAvailable() {

		return !tokenIsAvailable();
	}
}
