package io.hasura.core;

public class HasuraException extends Exception {
  private static final long serialVersionUID = 1;
  private int code;

  /**
   * Construct a new HasuraException with a particular error code.
   *
   * @param theCode
   *          The error code to identify the type of exception.
   * @param theMessage
   *          A message describing the error in more detail.
   */
  public HasuraException(int theCode, String theMessage) {
    super(theMessage);
    code = theCode;
  }

  /**
   * Construct a new HasuraException with an external cause.
   *
   * @param message
   *          A message describing the error in more detail.
   * @param cause
   *          The cause of the error.
   */
  public HasuraException(int theCode, String message, Throwable cause) {
    super(message, cause);
    code = theCode;
  }

  /**
   * Construct a new HasuraException with an external cause.
   *
   * @param cause
   *          The cause of the error.
   */
  public HasuraException(Throwable cause) {
    super(cause);
    code = 0;
  }

  /**
   * Access the code for this error.
   *
   * @return The numerical code for this error.
   */
  public int getCode() {
    return code;
  }
}
