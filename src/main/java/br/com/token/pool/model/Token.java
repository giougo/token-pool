package br.com.token.pool.model;

@SuppressWarnings("preview")
public record Token (String xAccessToken, int expiresInMilliseconds) {}
