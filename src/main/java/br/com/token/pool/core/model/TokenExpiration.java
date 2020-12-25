package br.com.token.pool.core.model;

import static br.com.token.pool.core.model.TokenExpirationStatus.EXPIRED;
import static br.com.token.pool.core.model.TokenExpirationStatus.NOT_EXPIRED;

import br.com.token.pool.core.utils.TimeUtils;

public class TokenExpiration {
	
	private static final long ZERO = 0L;

	private final TokenExpirationStatus expirationStatus;
	private final Token token;
	private final long validityOffsetInMilliseconds;
	private final long generationTimestamp;
	
	private TokenExpiration(Token token) {
		
		this(token, ZERO);
	}

	private TokenExpiration(Token token, long tokenMillisecondsExpirationOffset) {
		
		long nowTimestampMilliseconds = TimeUtils.getNowTimestamp();
		
		this.generationTimestamp = nowTimestampMilliseconds;
		
		this.token = token;
		
		this.validityOffsetInMilliseconds = nowTimestampMilliseconds - token.expirationTimestamp() - tokenMillisecondsExpirationOffset;
		
		if (validityOffsetInMilliseconds > 0) {
			
			this.expirationStatus = NOT_EXPIRED;
		
		} else {
			
			this.expirationStatus = EXPIRED;
		}
	}
	
	public static TokenExpiration from(Token token, long tokenMillisecondsExpirationOffset) {
		
		return new TokenExpiration(token, tokenMillisecondsExpirationOffset);
	}
	
	public static TokenExpiration from(Token token) {
		
		return new TokenExpiration(token);
	}

	public TokenExpirationStatus getExpirationStatus() {
		return expirationStatus;
	}

	public long getValidityOffsetInMilliseconds() {
		return validityOffsetInMilliseconds;
	}

	public long getGenerationTimestamp() {
		return generationTimestamp;
	}
	
	public Token getToken() {
		return token;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expirationStatus == null) ? 0 : expirationStatus.hashCode());
		result = prime * result + (int) (generationTimestamp ^ (generationTimestamp >>> 32));
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + (int) (validityOffsetInMilliseconds ^ (validityOffsetInMilliseconds >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TokenExpiration other = (TokenExpiration) obj;
		if (expirationStatus != other.expirationStatus)
			return false;
		if (generationTimestamp != other.generationTimestamp)
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		if (validityOffsetInMilliseconds != other.validityOffsetInMilliseconds)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TokenExpiration [expirationStatus=" + expirationStatus + ", token=" + token
				+ ", validityOffsetInMilliseconds=" + validityOffsetInMilliseconds + ", generationTimestamp="
				+ generationTimestamp + "]";
	}
}
