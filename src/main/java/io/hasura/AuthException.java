package io.hasura;

public class AuthException extends Exception {
    private static final long serialVersionUID = 1;
    private AuthError code;

    /**
     * Construct a new AuthException with a particular error code.
     *
     * @param theCode
     *          The error code to identify the type of exception.
     * @param theMessage
     *          A message describing the error in more detail.
     */
    public AuthException(AuthError theCode, String theMessage) {
        super(theMessage);
        code = theCode;
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

    @Override
    public String toString() {
        String message =
            AuthException.class.getName() + " "
            + code.toString() + " : " + super.getLocalizedMessage();
        return message;
    }
}
