package com.tscp.mvne.jms.request.payment;

import com.tscp.mvne.jms.request.Request;
import com.tscp.mvne.payment.transaction.CustPaymentTransaction;

public class PaymentRequestResponse extends Request {
  private static final long serialVersionUID = 8449138045310245156L;
  private int attemptNumber;
  private CustPaymentTransaction transaction = new CustPaymentTransaction();
  private PaymentType type;

  public int getAttemptNumber() {
    return attemptNumber;
  }

  public void setAttemptNumber(int attemptNumber) {
    this.attemptNumber = attemptNumber;
  }

  public CustPaymentTransaction getTransaction() {
    return transaction;
  }

  public void setTransaction(CustPaymentTransaction transaction) {
    this.transaction = transaction;
  }

  public PaymentType getType() {
    return type;
  }

  public void setType(PaymentType type) {
    this.type = type;
  }

  public boolean isSuccess() {
    return transaction.getMerchantResponse().isSuccess();
  }

}
