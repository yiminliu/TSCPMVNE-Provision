package com.tscp.mvne.customer.contact;

import java.io.Serializable;

public class ContactInfo implements Serializable {
  private static final long serialVersionUID = -5995522252550150854L;
  private CustomerAddress address = new CustomerAddress();
  private Contact contact = new Contact();

  public CustomerAddress getAddress() {
    return address;
  }

  public void setAddress(CustomerAddress address) {
    this.address = address;
  }

  public Contact getContact() {
    return contact;
  }

  public void setContact(Contact contact) {
    this.contact = contact;
  }

}
