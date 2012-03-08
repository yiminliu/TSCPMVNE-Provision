package com.tscp.mvne.network.exception;

import com.tscp.mvne.service.exception.FormattedException;

public class NetworkStatusException extends FormattedException {
  private static final long serialVersionUID = 7285588959701251499L;

  public NetworkStatusException(String pattern, Object... args) {
    super(pattern, args);
  }

  public NetworkStatusException(String message, Throwable cause) {
    super(message, cause);
  }

  public NetworkStatusException(String message) {
    super(message);
  }

  public NetworkStatusException(Throwable cause, String pattern, Object... args) {
    super(cause, pattern, args);
  }

}
