package io.hasura.core;

public class HasuraErrorResponse {
    private int code;
    private String message;

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
