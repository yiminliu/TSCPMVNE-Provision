package com.tscp.mvne.jms.request;

public interface JMSRequest {

  public void increaseAttempt();

  public int getAttemptNumber();

  public void setAttemptNumber(int attemptNumber);

  public RequestHistory getHistory();

  public void setHistory(RequestHistory history);
}
