package io.hasura.core;

public enum AuthError {
    INVALID_SESSION,
    UNAUTHORIZED,
    BAD_REQUEST,
    INTERNAL_ERROR,
    UNEXPECTED_CODE,
    CONNECTION_ERROR
}
