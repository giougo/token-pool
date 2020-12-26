package br.com.token.pool.core.model.token;

public class Token {
	
	String tokenId;
	String accessToken;
	Long expiresInMilliseconds;
	Long expirationTimestamp;
	
	public Token(String tokenId, String accessToken, Long expiresInMilliseconds, Long expirationTimestamp) {
		super();
		this.tokenId = tokenId;
		this.accessToken = accessToken;
		this.expiresInMilliseconds = expiresInMilliseconds;
		this.expirationTimestamp = expirationTimestamp;
	}

	public String getTokenId() {
		return tokenId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public Long getExpiresInMilliseconds() {
		return expiresInMilliseconds;
	}

	public Long getExpirationTimestamp() {
		return expirationTimestamp;
	}
}
