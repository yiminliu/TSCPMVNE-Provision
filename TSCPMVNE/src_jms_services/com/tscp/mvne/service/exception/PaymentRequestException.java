package com.tscp.mvne.service.exception;

import com.tscp.mvne.jms.request.payment.PaymentRequestType;

public class PaymentRequestException extends RequestException {
  private static final long serialVersionUID = -9206956697767904080L;

  public PaymentRequestException(PaymentRequestType requestType, String pattern, Object... args) {
    super(requestType.toString(), pattern, args);
  }

  public PaymentRequestException(PaymentRequestType requestType, String message, Throwable cause) {
    super(requestType.toString(), message, cause);
  }

  public PaymentRequestException(PaymentRequestType requestType, String message) {
    super(requestType.toString(), message);
  }

  public PaymentRequestException(PaymentRequestType requestType, Throwable cause, String pattern, Object... args) {
    super(requestType.toString(), cause, pattern, args);
  }

}
