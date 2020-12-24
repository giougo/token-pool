package br.com.token.pool.model;

@SuppressWarnings("preview")
public record Token (String accessToken, int expiresInMilliseconds) {}
