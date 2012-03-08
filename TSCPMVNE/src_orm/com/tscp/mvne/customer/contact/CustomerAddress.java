package com.tscp.mvne.customer.contact;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Temporarily not an entity because CustAddress class is used in legacy code.
 * This class should take it's place in the future.
 * 
 * @author Tachikoma
 * 
 */
@Entity
@Table(name = "cust_address")
public class CustomerAddress implements Serializable {
  private static final long serialVersionUID = 3078915125959610029L;
  private int AddressId;
  private int custId;
  private String label;
  private String address1;
  private String address2;
  private String address3;
  private String city;
  private String state;
  private String zip;
  private boolean empty;

  @Id
  @Column(name = "address_id")
  public int getAddressId() {
    return AddressId;
  }

  public void setAddressId(int addressId) {
    AddressId = addressId;
  }

  @Column(name = "cust_id")
  public int getCustId() {
    return custId;
  }

  public void setCustId(int custId) {
    this.custId = custId;
  }

  @Column(name = "address_label")
  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  @Column(name = "address1")
  public String getAddress1() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  @Column(name = "address2")
  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  @Column(name = "address3")
  public String getAddress3() {
    return address3;
  }

  public void setAddress3(String address3) {
    this.address3 = address3;
  }

  @Column(name = "city")
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Column(name = "state")
  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  @Column(name = "zip")
  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  @Transient
  public boolean isEmpty() {
    empty = getAddress1() == null ? true : getAddress1().isEmpty();
    return empty;
  }
}
