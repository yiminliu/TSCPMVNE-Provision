package com.tscp.mvne.billing.exception;

public class ProvisionException extends BillingException {
  private static final long serialVersionUID = 8276665812906694927L;

  public ProvisionException() {
    super();
  }

  public ProvisionException(String message, Exception e) {
    super(message, e);
  }

  public ProvisionException(String methodName, String message, Exception e) {
    super(methodName, message, e);
  }

  public ProvisionException(String methodName, String message) {
    super(methodName, message);
  }

  public ProvisionException(String message) {
    super(message);
  }

}
