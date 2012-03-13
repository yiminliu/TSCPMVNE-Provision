package com.tscp.mvne.billing.exception;

import com.tscp.mvne.exception.MVNEException;

public class NoResponseException extends MVNEException {
  private static final long serialVersionUID = 8452903212547415398L;

  public NoResponseException() {
    super();
  }

  public NoResponseException(String methodName, String message, Exception e) {
    super(methodName, message, e);
  }

  public NoResponseException(String methodName, String message) {
    super(methodName, message);
  }

  public NoResponseException(String message) {
    super(message);
  }

}
