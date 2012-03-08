package com.tscp.mvne.customer.payment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pmt_info")
public class PaymentInfo implements Serializable {
  private static final long serialVersionUID = 5282916700007271556L;
  private int paymentId;
  private String name;
  private Address address = new Address();

  @Id
  @Column(name = "pmt_id")
  public int getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(int paymentId) {
    this.paymentId = paymentId;
  }

  @Column(name = "cust_name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Embedded
  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

}