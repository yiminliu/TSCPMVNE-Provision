package com.tscp.mvne.payment.transaction;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PaymentSource implements Serializable {
  private static final long serialVersionUID = -2513326180117431838L;
  private int paymentId;
  private String source;
  private String method;

  @Column(name = "pmt_id")
  public int getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(int paymentId) {
    this.paymentId = paymentId;
  }

  @Column(name = "pmt_source")
  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  @Column(name = "pmt_method")
  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

}
