package com.tscp.mvne.exception;

import javax.xml.ws.WebServiceException;

public class MVNEException extends WebServiceException {
  private static final long serialVersionUID = -1984305003999836500L;
  private int transactionId;
  private String methodName;

  public MVNEException() {
    super();
  }

  public MVNEException(String message) {
    super(message);
  }

  public MVNEException(String methodName, String message) {
    super(message);
    setMethodname(methodName);
  }

  public MVNEException(String message, Exception e) {
    super(message + ". " + e.getMessage(), e.getCause());
  }

  public MVNEException(String methodName, String message, Exception e) {
    super(message + ". " + e.getMessage(), e.getCause());
    setMethodname(methodName);
  }

  public String getMethodname() {
    return methodName;
  }

  public int getTransactionid() {
    return transactionId;
  }

  public void setMethodname(String methodname) {
    this.methodName = methodname;
  }

  public void setTransactionid(int transactionid) {
    this.transactionId = transactionid;
  }

}
