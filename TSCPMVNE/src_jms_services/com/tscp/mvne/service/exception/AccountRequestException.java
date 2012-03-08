package com.tscp.mvne.service.exception;

import com.tscp.mvne.jms.request.account.AccountRequestType;

public class AccountRequestException extends RequestException {
  private static final long serialVersionUID = 9068857415690223299L;

  public AccountRequestException() {
    super();
  }

  public AccountRequestException(AccountRequestType requestType, String pattern, Object... args) {
    super(requestType.toString(), pattern, args);
  }

  public AccountRequestException(AccountRequestType requestType, String message, Throwable cause) {
    super(requestType.toString(), message, cause);
  }

  public AccountRequestException(AccountRequestType requestType, String message) {
    super(requestType.toString(), message);
  }

  public AccountRequestException(AccountRequestType requestType, Throwable cause, String pattern, Object... args) {
    super(requestType.toString(), cause, pattern, args);
  }

}
