package com.tscp.mvne.service.exception;

import com.tscp.mvne.jms.request.account.ServiceInstanceRequestType;

public class ServiceInstanceRequestException extends RequestException {
  private static final long serialVersionUID = 3943519170095168649L;

  public ServiceInstanceRequestException(ServiceInstanceRequestType requestType, String pattern, Object... args) {
    super(requestType.toString(), pattern, args);
  }

  public ServiceInstanceRequestException(ServiceInstanceRequestType requestType, String message, Throwable cause) {
    super(requestType.toString(), message, cause);
  }

  public ServiceInstanceRequestException(ServiceInstanceRequestType requestType, String message) {
    super(requestType.toString(), message);
  }

  public ServiceInstanceRequestException(ServiceInstanceRequestType requestType, Throwable cause, String pattern,
    Object... args) {
    super(requestType.toString(), cause, pattern, args);
  }

}
