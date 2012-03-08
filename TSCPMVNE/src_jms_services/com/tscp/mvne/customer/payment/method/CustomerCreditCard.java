package com.tscp.mvne.customer.payment.method;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pmt_creditcard")
public class CustomerCreditCard implements Serializable {
  private static final long serialVersionUID = 868304184228331328L;
  private int paymentId;
  private String cardNumer;
  private String cvv;
  private Date expirationDate;

  @Id
  @Column(name = "pmt_id")
  public int getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(int paymentId) {
    this.paymentId = paymentId;
  }

  @Column(name = "creditcard_no")
  public String getCardNumer() {
    return cardNumer;
  }

  public void setCardNumer(String cardNumer) {
    this.cardNumer = cardNumer;
  }

  @Column(name = "sec_code")
  public String getCvv() {
    return cvv;
  }

  public void setCvv(String cvv) {
    this.cvv = cvv;
  }

  @Column(name = "exp_date")
  public Date getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(Date expirationDate) {
    this.expirationDate = expirationDate;
  }

}