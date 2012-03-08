package com.tscp.mvne.jms.request;

import java.io.Serializable;

public class RequestHistory implements Serializable {
  private static final long serialVersionUID = 613539992188943723L;
  protected String lastExceptionMessage;
  protected String failureDescription;
  protected RequestType failureType;

  public String getLastExceptionMessage() {
    return lastExceptionMessage;
  }

  public void setLastExceptionMessage(String lastExceptionMessage) {
    this.lastExceptionMessage = lastExceptionMessage;
  }

  public String getFailureDescription() {
    return failureDescription;
  }

  public void setFailureDescription(String failureDescription) {
    this.failureDescription = failureDescription;
  }

  public RequestType getFailureType() {
    return failureType;
  }

  public void setFailureType(RequestType failureType) {
    this.failureType = failureType;
  }

}
