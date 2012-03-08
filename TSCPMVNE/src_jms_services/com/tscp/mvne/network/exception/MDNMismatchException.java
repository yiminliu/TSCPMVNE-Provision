package com.tscp.mvne.network.exception;

import com.tscp.mvne.service.exception.FormattedException;

public class MDNMismatchException extends FormattedException {
  private static final long serialVersionUID = 2807786044911544292L;

  public MDNMismatchException(String pattern, Object... args) {
    super(pattern, args);
  }

  public MDNMismatchException(String message, Throwable cause) {
    super(message, cause);
  }

  public MDNMismatchException(String message) {
    super(message);
  }

  public MDNMismatchException(Throwable cause, String pattern, Object... args) {
    super(cause, pattern, args);
  }

}
