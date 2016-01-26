package io.hasura.core;

public enum AuthError {
    INVALID_SESSION,
    UNAUTHORIZED,
    BAD_REQUEST,
    REQUEST_FAILED,
    INTERNAL_ERROR,
    UNEXPECTED_CODE,
    CONNECTION_ERROR
}
