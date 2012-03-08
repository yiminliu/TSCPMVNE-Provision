package com.tscp.mvne.jms.request;

import java.io.Serializable;

public abstract class Request implements JMSRequest, Serializable {
  private static final long serialVersionUID = 8775057623599239898L;
  protected int attemptNumber = 1;
  protected RequestHistory history = new RequestHistory();

  @Override
  public void increaseAttempt() {
    attemptNumber++;
  }

  @Override
  public int getAttemptNumber() {
    return attemptNumber;
  }

  @Override
  public void setAttemptNumber(int attemptNumber) {
    this.attemptNumber = attemptNumber;
  }

  @Override
  public RequestHistory getHistory() {
    return history;
  }

  @Override
  public void setHistory(RequestHistory history) {
    this.history = history;
  }

}