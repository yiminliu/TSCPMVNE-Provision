package com.tscp.mvne.billing.exception;

public class ServiceProvisionedException extends BillingException {
  private static final long serialVersionUID = 4222422785676267822L;

  public ServiceProvisionedException() {
    super();
  }

  public ServiceProvisionedException(String methodName, String message, Exception e) {
    super(methodName, message, e);
  }

  public ServiceProvisionedException(String methodName, String message) {
    super(methodName, message);
  }

  public ServiceProvisionedException(String message) {
    super(message);
  }

}
