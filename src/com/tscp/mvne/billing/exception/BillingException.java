package com.tscp.mvne.billing.exception;

import com.tscp.mvne.exception.MVNEException;

public class BillingException extends MVNEException {
  private static final long serialVersionUID = 3743360978816790260L;
  private int accountNumber;
  private String externalId;

  public BillingException() {
    super();
  }

  public BillingException(String message, Exception e) {
    super(message, e);
  }

  public BillingException(String methodName, String message, Exception e) {
    super(methodName, message, e);
  }

  public BillingException(String methodName, String message) {
    super(methodName, message);
  }

  public BillingException(String message) {
    super(message);
  }

  public int getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(int accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getExternalId() {
    return externalId;
  }

  public void setExternalId(String externalId) {
    this.externalId = externalId;
  }

}