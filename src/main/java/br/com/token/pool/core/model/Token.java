package br.com.token.pool.core.model;

@SuppressWarnings("preview")
public record Token (String tokenId, String accessToken, Long expiresInMilliseconds, Long expirationTimestamp) {}
