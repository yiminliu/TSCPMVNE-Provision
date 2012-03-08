package com.tscp.mvne.service.exception;


public abstract class RequestException extends FormattedException {
  private static final long serialVersionUID = -2563065763225880903L;
  private String requestType;

  public RequestException() {
    super();
  }

  public RequestException(String requestType, String pattern, Object... args) {
    super(pattern, args);
    this.requestType = requestType;
  }

  public RequestException(String requestType, String message, Throwable cause) {
    super(message, cause);
    this.requestType = requestType;
  }

  public RequestException(String requestType, String message) {
    super(message);
    this.requestType = requestType;
  }

  public RequestException(String requestType, Throwable cause, String pattern, Object... args) {
    super(cause, pattern, args);
    this.requestType = requestType;
  }

  public String getRequestType() {
    return requestType;
  }

  public void setRequestType(String requestType) {
    this.requestType = requestType;
  }

}
