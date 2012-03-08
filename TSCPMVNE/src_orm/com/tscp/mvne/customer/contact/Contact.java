package com.tscp.mvne.customer.contact;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "cust_contact")
public class Contact implements Serializable {
  private static final long serialVersionUID = -2809566194797396455L;
  private int custId;
  private String firstName;
  private String middleName;
  private String lastName;
  private String phoneNumber;
  private String email;

  @Id
  @Column(name = "cust_id")
  public int getCustId() {
    return custId;
  }

  public void setCustId(int custId) {
    this.custId = custId;
  }

  @Column(name = "firstname")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Column(name = "middlename")
  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  @Column(name = "lastname")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Column(name = "phone")
  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Column(name = "email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Transient
  public boolean isEmpty() {
    return getFirstName().isEmpty() || getLastName().isEmpty() || getEmail().isEmpty();
  }
}
