package com.tscp.mvne.service.exception;

import com.tscp.mvne.jms.request.network.NetworkRequestType;

public class NetworkRequestException extends RequestException {
  private static final long serialVersionUID = 8064366041324997072L;

  public NetworkRequestException(NetworkRequestType requestType, String pattern, Object... args) {
    super(requestType.toString(), pattern, args);
  }

  public NetworkRequestException(NetworkRequestType requestType, String message, Throwable cause) {
    super(requestType.toString(), message, cause);
  }

  public NetworkRequestException(NetworkRequestType requestType, String message) {
    super(requestType.toString(), message);
  }

  public NetworkRequestException(NetworkRequestType requestType, Throwable cause, String pattern, Object... args) {
    super(requestType.toString(), cause, pattern, args);
  }

}
