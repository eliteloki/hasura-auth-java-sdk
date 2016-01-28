package io.hasura;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.lang.reflect.Type;

public class AuthResponseConverter<T> implements Converter<T, AuthException> {

    static class AuthErrorResponse {
        private int code;
        private String message;
        private JsonObject info;
        private String error_code;

        public JsonObject getInfo() {
            return info;
        }

        public String getErrorCode() {
            return error_code;
        }

        public int getCode() {
            return this.code;
        }

        public String getMessage() {
            return this.message;
        }
    }

    private final Type resType;
    public AuthResponseConverter(Type resType) {
        this.resType = resType;
    }

    @Override
    public T fromResponse(okhttp3.Response response) throws AuthException {
        int code = response.code();

        try {
            if (code == 200) {
                return Util.parseJson(response, resType);
            }
            else {
                AuthErrorResponse err = Util.parseJson(response, AuthErrorResponse.class);
                AuthError errCode;
                switch (code) {
                case 400:
                    errCode = AuthError.BAD_REQUEST;
                    break;
                case 401:
                    errCode = AuthError.UNAUTHORIZED;
                    break;
                case 402:
                    errCode = AuthError.REQUEST_FAILED;
                    break;
                case 403:
                    errCode = AuthError.INVALID_SESSION;
                    break;
                case 500:
                    errCode = AuthError.INTERNAL_ERROR;
                    break;
                default:
                    errCode = AuthError.UNEXPECTED_CODE;
                    break;
                }
                throw new AuthException(errCode, err.getMessage(), err.getInfo(), err.getErrorCode());
            }
        }
        catch (HasuraJsonException e) {
            throw new AuthException(AuthError.INTERNAL_ERROR, e);
        }
    }

    @Override
    public AuthException fromIOException(IOException e) {
        return new AuthException(AuthError.CONNECTION_ERROR, e);
    }

    @Override
    public AuthException castException(Exception e) {
        return (AuthException) e;
    }
}
