package com.tscp.mvne.customer.payment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cust_pmt_map")
public class PaymentMap implements Serializable {
  private static final long serialVersionUID = -1205827665967735948L;
  private int paymentId;
  private int customerId;
  private String alias;
  private String type;
  private boolean defaultMethod;

  @Id
  @Column(name = "pmt_id")
  public int getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(int paymentId) {
    this.paymentId = paymentId;
  }

  @Column(name = "cust_id")
  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  @Column(name = "pmt_alias")
  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  @Column(name = "pmt_type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Column(name = "is_default")
  public boolean isDefaultMethod() {
    return defaultMethod;
  }

  public void setDefaultMethod(boolean defaultMethod) {
    this.defaultMethod = defaultMethod;
  }
}