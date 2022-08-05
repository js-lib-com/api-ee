package com.jslib.api.transaction;

/**
 * Transaction handling or transactional working unit fails to complete properly.
 * 
 * @author Iulian Rotaru
 * @version final
 */
public class TransactionException extends RuntimeException
{
  /** Java serialization version. */
  private static final long serialVersionUID = 1202056117987925916L;

  /**
   * Construct exception with formatted message. See {@link String#format(String, Object...)} for message format
   * description.
   * 
   * @param message formatted message,
   * @param args optional format arguments.
   */
  public TransactionException(String message, Object... args)
  {
    super(String.format(message, args));
  }

  /**
   * Create working unit exception with given cause.
   * 
   * @param cause exception cause.
   */
  public TransactionException(Exception cause)
  {
    super(cause);
  }
}
