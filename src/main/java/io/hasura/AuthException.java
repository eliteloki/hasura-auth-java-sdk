package io.hasura;

import com.google.gson.JsonObject;

public class AuthException extends Exception {
    private static final long serialVersionUID = 1;
    private AuthError code;
    private JsonObject info;
    private String errorCode;

    /**
     * Construct a new AuthException with a particular error code.
     *
     * @param theCode
     *          The error code to identify the type of exception.
     * @param theMessage
     *          A message describing the error in more detail.
     * @param theInfo
     *          The Info contains additional data.
     * @param theErrorCode
     *          A code for checking auth error.
     */
    public AuthException(AuthError theCode, String theMessage, JsonObject theInfo, String theErrorCode) {
        super(theMessage);
        code = theCode;
        info = theInfo;
        errorCode = theErrorCode;
    }

    /**
     * Construct a new AuthException with a particular error code.
     *
     * @param theCode
     *          The error code to identify the type of exception.
     * @param cause
     *          The cause of the error.
     */
    public AuthException(AuthError theCode, Throwable cause) {
        super(cause);
        code = theCode;
    }

    /**
     * Access the code for this error.
     *
     * @return The code for this error.
     */
    public AuthError getCode() {
        return code;
    }



    /**
     * Access the info for this error.
     *
     * @return The info for this error.
     */
    public JsonObject getInfo() {
        return info;
    }


    /**
     * Access the error-code for this error.
     *
     * @return The error-code for this error.
     */
    public String getErrorCode() {
        return errorCode;
    }



    @Override
    public String toString() {
        String message =
            AuthException.class.getName() + " "
            + code.toString() + " : " + super.getLocalizedMessage();
        return message;
    }
}
