package com.tscp.mvne.network.exception;

import com.tscp.mvne.service.exception.FormattedException;

public class ESNMismatchException extends FormattedException {
  private static final long serialVersionUID = -1167042365153901544L;

  public ESNMismatchException(String pattern, Object... args) {
    super(pattern, args);
  }

  public ESNMismatchException(String message, Throwable cause) {
    super(message, cause);
  }

  public ESNMismatchException(String message) {
    super(message);
  }

  public ESNMismatchException(Throwable cause, String pattern, Object... args) {
    super(cause, pattern, args);
  }

}
