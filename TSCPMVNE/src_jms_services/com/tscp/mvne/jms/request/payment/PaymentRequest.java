package com.tscp.mvne.jms.request.payment;

import com.tscp.mvne.customer.payment.method.CustomerCreditCard;
import com.tscp.mvne.jms.request.Request;

public class PaymentRequest extends Request {
  private static final long serialVersionUID = -601273110876902491L;
  private int customerId;
  private int accountNumber;
  private double amount;
  private int attemptNumber;
  private String sessionId;
  private String mdn;
  private String confirmationCode;
  private String trackingId;
  private PaymentType type;
  private PaymentRequestType requestType;
  private CustomerCreditCard creditCard = new CustomerCreditCard();

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public int getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(int accountNumber) {
    this.accountNumber = accountNumber;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public int getAttemptNumber() {
    return attemptNumber;
  }

  public void setAttemptNumber(int attemptNumber) {
    this.attemptNumber = attemptNumber;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public String getMdn() {
    return mdn;
  }

  public void setMdn(String mdn) {
    this.mdn = mdn;
  }

  public CustomerCreditCard getCreditCard() {
    return creditCard;
  }

  public void setCreditCard(CustomerCreditCard creditCard) {
    this.creditCard = creditCard;
  }

  public String getConfirmationCode() {
    return confirmationCode;
  }

  public void setConfirmationCode(String confirmationCode) {
    this.confirmationCode = confirmationCode;
  }

  public String getTrackingId() {
    return trackingId;
  }

  public void setTrackingId(String trackingId) {
    this.trackingId = trackingId;
  }

  public PaymentType getType() {
    return type;
  }

  public void setType(PaymentType type) {
    this.type = type;
  }

  public PaymentRequestType getRequestType() {
    return requestType;
  }

  public void setRequestType(PaymentRequestType requestType) {
    this.requestType = requestType;
  }

  @Override
  public String toString() {
    return "PaymentRequest [customerId=" + customerId + ", accountNumber=" + accountNumber + ", amount=" + amount
        + ", attemptNumber=" + attemptNumber + ", sessionId=" + sessionId + ", mdn=" + mdn + ", confirmationCode="
        + confirmationCode + ", trackingId=" + trackingId + ", type=" + type + ", requestType=" + requestType + ", creditCard="
        + creditCard + ", history=" + history + "]";
  }

}