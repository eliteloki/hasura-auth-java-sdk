package io.hasura.core;

import java.io.IOException;
import java.lang.reflect.Type;

public class AuthConverter<T> implements Converter<T, HasuraAuthException> {

    static class AuthError {
        private int code;
        private String message;

        public int getCode() {
            return this.code;
        }

        public String getMessage() {
            return this.message;
        }
    }

    private final Type bodyType;
    public AuthConverter(Type bodyType) {
        this.bodyType = bodyType;
    }

    @Override
    public T fromResponse(okhttp3.Response response) throws HasuraAuthException {
        int code = response.code();

        try {
            if (code == 200) {
                return Util.parseJson(response, bodyType);
            }
            else {
                AuthError err = Util.parseJson(response, AuthError.class);
                throw new HasuraJsonException(code, err.getMessage());
            }
        }
        catch (HasuraJsonException e) {
            throw new HasuraAuthException(e);
        }
    }

    @Override
    public HasuraAuthException fromIOException(IOException e) {
        return new HasuraAuthException(e);
    }

    @Override
    public HasuraAuthException castException(Exception e) {
        return (HasuraAuthException) e;
    }
}
