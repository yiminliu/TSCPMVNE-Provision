package com.tscp.mvne.billing.exception;

public class BillingServerException extends BillingException {
  private static final long serialVersionUID = -552373794868039645L;

  public BillingServerException() {
    super();
  }

  public BillingServerException(String message, Exception e) {
    super(message, e);
  }

  public BillingServerException(String methodName, String message, Exception e) {
    super(methodName, message, e);
  }

  public BillingServerException(String methodName, String message) {
    super(methodName, message);
  }

  public BillingServerException(String message) {
    super(message);
  }

}
