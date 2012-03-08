package com.tscp.mvne.customer.payment;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
  private String address1;
  private String address2;
  private String city;
  private String state;
  private int zip;

  @Column(name = "bill_addr1")
  public String getAddress1() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  @Column(name = "bill_addr2")
  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  @Column(name = "bill_city")
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Column(name = "bill_state")
  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  @Column(name = "bill_zip")
  public int getZip() {
    return zip;
  }

  public void setZip(int zip) {
    this.zip = zip;
  }

}